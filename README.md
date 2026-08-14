# CS 320 Portfolio – Jeri Mabuti

**Course:** CS 320: Software Test, Automation QA — Southern New Hampshire University
**Instructor:** Dr. Fernando Nieto, DCS

This repository holds both deliverables from CS 320: the Project One codebase (Contact, Task, and Appointment services) and the Project Two summary and reflections report written about that codebase.

## Contents

- [Project One: Contact, Task & Appointment Services](#project-one-contact-task--appointment-services)
  - [Overview](#overview)
  - [Project Structure](#project-structure)
  - [Validation Rules](#validation-rules)
  - [Service Behavior](#service-behavior)
  - [Testing](#testing)
  - [How to Run](#how-to-run)
  - [Limitations](#limitations)
- [Project Two: Summary and Reflections Report](#project-two-summary-and-reflections-report)
  - [Summary](#summary)
  - [Reflection](#reflection)
  - [References](#references)

---

## Project One: Contact, Task & Appointment Services

### Overview

This repository contains three independent, in-memory backend services built for Grand Strand Systems as the back-end layer for a mobile application: a **Contact Service**, a **Task Service**, and an **Appointment Service**. Each service is a standalone Eclipse Java project consisting of a data class, a service class that manages a collection of that data, and a JUnit 5 test suite covering both.

The three services share no code or dependencies. Each stores its objects in memory only (no database or file persistence) and is exercised entirely through its JUnit test suite; there is no UI layer.

### Project Structure

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

### Validation Rules

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

### Service Behavior

Each service stores its objects in a `HashMap<String, T>` keyed by ID, giving O(1) add, delete, and lookup regardless of collection size, and using the key itself to enforce ID uniqueness (no separate scan needed).

| Service | Operations |
|---|---|
| `ContactService` | `addContact` (rejects duplicate IDs), `deleteContact`, `updateFirstName`, `updateLastName`, `updatePhone`, `updateAddress` |
| `TaskService` | `addTask` (rejects duplicate IDs), `deleteTask`, `updateName`, `updateDescription` |
| `AppointmentService` | `addAppointment` (rejects duplicate IDs), `deleteAppointment`, `getAppointment` |

All four `updateX` methods on `ContactService` and both `updateX` methods on `TaskService` route through a private `getContact()` / `getTask()` helper, which throws `IllegalArgumentException` if the ID isn't found. `AppointmentService` has no update methods by design — an appointment's date or description can't be changed after creation, only added or removed.

**Note on delete behavior:** `ContactService.deleteContact()` and `TaskService.deleteTask()` silently no-op if the ID doesn't exist (`Map.remove` on a missing key just returns `null`). `AppointmentService.deleteAppointment()`, by contrast, explicitly checks first and **throws** `IllegalArgumentException` if the ID isn't found. This is an intentional inconsistency worth being aware of if the services are ever unified behind a common interface.

### Testing

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

### How to Run

**In Eclipse:**
1. `File → Import → Existing Projects into Workspace`, and select each of the three folders (`ContactService`, `TaskService`, `AppointmentService`) individually — they are separate projects.
2. Right-click a test class (e.g. `ContactTest.java`) → `Run As → JUnit Test`. Right-click the `src` folder to run an entire project's suite at once.

**From the command line** (requires the JUnit 5 Console Launcher / Platform Console Standalone jar on the classpath):
```
javac -d bin -cp junit-platform-console-standalone.jar src/*.java
java -jar junit-platform-console-standalone.jar --classpath bin --scan-classpath
```
Run this separately inside each service folder (`ContactService`, `TaskService`, `AppointmentService`).

### Limitations

- In-memory only — no database or file persistence; all data is lost when the JVM exits.
- No shared interface or common parent between the three services; each was built and tested independently per the Project One requirements.
- No integration, system, or exploratory testing has been performed — only unit-level testing of each class in isolation (see the Project Two reflections report below for the reasoning behind that scope).

---

## Project Two: Summary and Reflections Report

*Submitted August 14, 2026*

### Summary

#### I. Testing Approach for the Contact Service

My testing approach for Contact and ContactService came directly from the stated requirements. Every field has an explicit boundary (IDs and names no more than 10 characters, phone exactly 10 digits, address no more than 30 characters), tested at the limit, one past it, and a null, e.g. `testContactIDTooLong()`. Phone needed a fourth case, "123-456-79," since a length-only check would miss a non-digit 10-character string. ContactService tests covered adding a unique contact, rejecting duplicates, deleting, and updating all four fields whether or not the contact exists.

#### II. Testing Approach for the Task Service

Task Service follows the same pattern with its own limits: ID no more than 10 characters and not updatable, name no more than 20 characters, description no more than 50 characters, each verified with matching boundary pairs. TaskService mirrors ContactService (add with a uniqueness check, delete, update name/description), including confirming a never-added task ID doesn't throw on delete (`testDeleteNonExistentTask_DoesNotThrow`), a deliberate design choice I wanted locked in with a test.

#### III. Testing Approach for the Appointment Service

Appointment service introduced a requirement the others didn't: a date that can't be in the past, validated with `Date.before(new Date())`. Since "the past" is relative to runtime, I wrote `futureDate()` and `pastDate()` helpers so `testAppointmentDateInPast()` stays valid no matter when the suite runs. AppointmentService only required add and delete, tested along with duplicate-ID rejection and a multi-appointment scenario confirming that deleting one of four appointments doesn't corrupt the rest of the map.

#### IV. Defending the Effectiveness of the Test Suite

I know these tests are effective because every conditional branch across the six classes has a test exercising it, every throwing condition and every success path proven. Coverage in Codio confirmed 80%+ on each file, branch by branch rather than one aggregate number. Contact Service ended with 30 tests, Task Service with 19, and Appointment Service with 15, for 64 total, all passing, with most requirements getting both a positive and a negative test.

#### V. Writing Technically Sound and Efficient Code

For technical soundness, I centralized validation logic inside each class's setters and had constructors call those setters instead of duplicating logic inline, so there is exactly one place deciding whether a value is valid:

```java
public Contact(String contactID, String firstName, String lastName, String phone, String address) {
    if (contactID == null || contactID.length() > 10) {
        throw new IllegalArgumentException("Invalid contact ID");
    }
    this.contactID = contactID;
    setFirstName(firstName);
    setLastName(lastName);
    setPhone(phone);
    setAddress(address);
}
```

For efficiency, all three services store objects in a `HashMap<String, T>` keyed by ID, keeping add, delete, and lookup at O(1) regardless of size, and enforcing uniqueness through the key itself rather than a separate duplicate-scanning loop:

```java
private Map<String, Contact> contacts = new HashMap<>();

public void addContact(Contact contact) {
    if (contacts.containsKey(contact.getContactID())) {
        throw new IllegalArgumentException("Contact ID already exists");
    }
    contacts.put(contact.getContactID(), contact);
}
```

Both patterns, constructor-calls-setter and HashMap-by-ID, repeat across Task and Appointment as well.

### Reflection

#### I. Testing Techniques Employed

The two techniques I leaned on most were boundary value analysis and equivalence partitioning (Hambling et al., 2019). Boundary value analysis tests the edges of a valid range, where off-by-one errors hide, exactly what my exactly-10/11-character pairs test. Equivalence partitioning groups inputs into classes that behave alike and tests one representative from each; for phone that meant one test from "too short," "too long," "wrong characters," and "valid," plus negative testing via `assertThrows`.

#### II. Testing Techniques Not Employed

I didn't use integration, system, or exploratory testing, each a deliberate scope call. Integration testing verifies components once wired together, but Project One kept Contact, Task, and Appointment fully independent. System testing evaluates the whole application end to end, which doesn't apply without a UI, and exploratory testing needs a running application to click through, not three isolated backend classes.

#### III. Practical Uses and Implications of These Techniques

Boundary value analysis and equivalence partitioning are strongest at the unit level, cheap to write and effective at catching off-by-one and validation errors before code ships. Integration testing matters once these services share a UI or database, since defects can come from component interaction. Exploratory testing pays off at the latest, once there's a build to click through and surface issues a checklist wouldn't catch. A small, isolated library benefits most from unit testing; a customer-facing application needs all three.

#### IV. Mindset – Complexity and Caution

I stayed aware that a small change in one method can ripple into a class's other guarantees. The clearest example is the "not updatable" ID fields: there is no `setContactID()`, `setTaskID()`, or `setAppointmentID()`, so validation only happens in the constructor, coupling immutability to that logic. Adding a setter later would silently break that guarantee, which is why I wrote `testContactIDNotUpdateable()` and its equivalents.

#### V. Mindset – Limiting Bias

Testing my own code carries an obvious risk: I already know how I intended it to behave, so it's easy to write tests that confirm my assumptions rather than challenge them. A developer tends to see their own deliverable as correct because they built it, while an independent tester assumes it's flawed until proven otherwise (Hambling et al., 2019). Without an independent tester available, I substituted independence of mind, writing every test from the requirements wording and deliberately feeding each method the input I'd least expect, which is how I caught the phone field's non-digit case.

#### VI. Mindset – Discipline

Being disciplined about testing keeps small shortcuts from becoming technical debt. A happy-path-only suite would have been faster to write, but defects rarely show up when everything goes right, and the negative and boundary cases I wrote upfront are what keep me from leaving landmines for a future version of myself, or a teammate.

### References

Hambling, B., Morgan, P., Samaroo, A., Thompson, G., & Williams, P. (2019). *Software testing: An ISTQB-BCS certified tester foundation guide* (4th ed.). BCS Learning & Development Limited.
