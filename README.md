# CS 320 – Project One: Contact, Task & Appointment Services

**Author:** Jeri Mabuti
**Course:** CS 320: Software Test, Automation QA — Southern New Hampshire University

## Overview

This repository contains three independent, in-memory backend services built for Grand Strand Systems as the back-end layer for a mobile application: a **Contact Service**, a **Task Service**, and an **Appointment Service**. Each service is a standalone Eclipse Java project consisting of a data class, a service class that manages a collection of that data, and a JUnit 5 test suite covering both.

The three services share no code or dependencies. Each stores its objects in memory only (no database or file persistence) and is exercised entirely through its JUnit test suite; there is no UI layer.

## Project Structure

```
ContactService/
  src/
    Contact.java          # data class + field validation
    ContactService.java   # CRUD operations over a Map<String, Contact>
    ContactTest.java       # unit tests for Contact
    ContactServiceTest.java # unit tests for ContactService

TaskService/
  src/
    Task.java
    TaskService.java
    TaskTest.java
    TaskServiceTest.java

AppointmentService/
  src/
    Appointment.java
    AppointmentService.java
    AppointmentTest.java
    AppointmentServiceTest.java
```

Each folder is a self-contained Eclipse project (`.project` / `.classpath`) targeting **Java SE 21**, with JUnit 5 provided via Eclipse's built-in JUnit container.

## Validation Rules

| Entity | Field | Rule |
|---|---|---|
| Contact | `contactID` | Required, ≤ 10 characters, **immutable** after creation (no setter) |
| Contact | `firstName` / `lastName` | Required, ≤ 10 characters each |
| Contact | `phone` | Required, exactly 10 digits (`\d{10}`) |
| Contact | `address` | Required, ≤ 30 characters |
| Task | `taskID` | Required, ≤ 10 characters, **immutable** after creation |
| Task | `name` | Required, ≤ 20 characters |
| Task | `description` | Required, ≤ 50 characters |
| Appointment | `appointmentID` | Required, ≤ 10 characters, **immutable** after creation |
| Appointment | `appointmentDate` | Required, must not be in the past (`date.before(new Date())` fails validation) |
| Appointment | `description` | Required, ≤ 50 characters |

All validation lives in each field's setter, and each constructor calls those setters (instead of duplicating the checks) so there is exactly one place per field deciding whether a value is valid. The ID field is the one exception: since there is no `setContactID()` / `setTaskID()` / `setAppointmentID()`, ID validation only happens in the constructor, which is what makes the ID immutable after creation.

## Service Behavior

Each service stores its objects in a `HashMap<String, T>` keyed by ID, giving O(1) add, delete, and lookup regardless of collection size, and using the key itself to enforce ID uniqueness (no separate scan needed).

| Service | Operations |
|---|---|
| `ContactService` | `addContact` (rejects duplicate IDs), `deleteContact`, `updateFirstName`, `updateLastName`, `updatePhone`, `updateAddress` |
| `TaskService` | `addTask` (rejects duplicate IDs), `deleteTask`, `updateName`, `updateDescription` |
| `AppointmentService` | `addAppointment` (rejects duplicate IDs), `deleteAppointment`, `getAppointment` |

All four `updateX` methods on `ContactService` and both `updateX` methods on `TaskService` route through a private `getContact()` / `getTask()` helper, which throws `IllegalArgumentException` if the ID isn't found. `AppointmentService` has no update methods by design — an appointment's date or description can't be changed after creation, only added or removed.

**Note on delete behavior:** `ContactService.deleteContact()` and `TaskService.deleteTask()` silently no-op if the ID doesn't exist (`Map.remove` on a missing key just returns `null`). `AppointmentService.deleteAppointment()`, by contrast, explicitly checks first and **throws** `IllegalArgumentException` if the ID isn't found. This is an intentional inconsistency worth being aware of if the services are ever unified behind a common interface.

## Testing

Built with **JUnit 5**. 64 tests total, all passing:

| Test class | Tests |
|---|---|
| `ContactTest` | 18 |
| `ContactServiceTest` | 12 |
| `TaskTest` | 11 |
| `TaskServiceTest` | 8 |
| `AppointmentTest` | 10 |
| `AppointmentServiceTest` | 5 |
| **Total** | **64** |

Each field's boundary is tested at the limit, one past the limit, and with a `null` input, plus positive/negative coverage for every service operation (add, duplicate rejection, delete, delete-of-missing-ID, and update on both an existing and a non-existent ID). The `Appointment` tests additionally use `futureDate()` / `pastDate()` helper methods (built off `System.currentTimeMillis()`) so date-boundary tests stay valid no matter when the suite is run, rather than relying on a hardcoded date.

## How to Run

**In Eclipse:**
1. `File → Import → Existing Projects into Workspace`, and select each of the three folders (`ContactService`, `TaskService`, `AppointmentService`) individually — they are separate projects.
2. Right-click a test class (e.g. `ContactTest.java`) → `Run As → JUnit Test`. Right-click the `src` folder to run an entire project's suite at once.

**From the command line** (requires the JUnit 5 Console Launcher / Platform Console Standalone jar on the classpath):
```
javac -d bin -cp junit-platform-console-standalone.jar src/*.java
java -jar junit-platform-console-standalone.jar --classpath bin --scan-classpath
```
Run this separately inside each service folder (`ContactService`, `TaskService`, `AppointmentService`).

## Limitations

- In-memory only — no database or file persistence; all data is lost when the JVM exits.
- No shared interface or common parent between the three services; each was built and tested independently per the Project One requirements.
- No integration, system, or exploratory testing has been performed — only unit-level testing of each class in isolation (see the Project Two reflections report for the reasoning behind that scope).
