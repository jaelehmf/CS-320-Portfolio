/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

class AppointmentServiceTest {

	private AppointmentService service;
	
	private Date futureDate() {
		return new Date(System.currentTimeMillis() + 100000);
	}
	
	/*
	 * runs before each test so it starts with a clean/empty service
	 * prevents tests from accidentally depending on leftover state
	 */
	@BeforeEach
	void setUp() {
		service = new AppointmentService();
	}
	
	// add testing 
	
	@Test
	void testAppointment_Succeeds() {
		Appointment a = new Appointment("1111111111", futureDate(), "AV Dispatch stand up");
		service.addAppointment(a);
		//proves it was stored by fetching it back out
		assertEquals(a, service.getAppointment("1111111111"));
	}
	
	@Test
	void testAddDuplicateAppointmentID_Throws() {
		Appointment a1 = new Appointment("1111111111", futureDate(), "AV Dispatch stand up");
		Appointment a2 = new Appointment("1111111111", futureDate(), "Sleep clinic consultation");
		service.addAppointment(a1);
		// a2 has the same ID as a1, this should be rejected to meet unique ID requirement
		assertThrows(IllegalArgumentException.class, () ->
			service.addAppointment(a2));
	}
	
	// deletion testing
	
	@Test
	void testDeleteAppointment_RemovesID() {
		Appointment a = new Appointment("2222222222", futureDate(), "CS 320 tutoring session");
		service.addAppointment(a);
		service.deleteAppointment("2222222222");
		// after deletion, that ID should no longer be found
		assertNull(service.getAppointment("2222222222"));
	}

	@Test
	void testDeleteNonExistentAppointment_Throws() {
		// deleting an ID that was never added should be rejected
		assertThrows(IllegalArgumentException.class, () ->
			service.deleteAppointment("99999999999"));
	}
	
	@Test
	void testAddAndDeleteMultipleAppointments() {
		// pulls all four TaskService themes into one flow: work, sleep, school, wedding
		Appointment work = new Appointment("3333333333", futureDate(), "AV Dispatch stand up");
		Appointment sleep = new Appointment("4444444444", futureDate(), "Sleep clinic consultation");
		Appointment school = new Appointment("5555555555", futureDate(), "CS 320 tutoring session");
		Appointment wedding = new Appointment("6666666666", futureDate(), "Wedding venue walkthrough");
		
		service.addAppointment(work);
		service.addAppointment(sleep);
		service.addAppointment(school);
		service.addAppointment(wedding);
		
		service.deleteAppointment("4444444444");
		
		assertNotNull(service.getAppointment("3333333333"));
		assertNull(service.getAppointment("4444444444"));
		assertNotNull(service.getAppointment("5555555555"));
		assertNotNull(service.getAppointment("6666666666"));
	}
}





