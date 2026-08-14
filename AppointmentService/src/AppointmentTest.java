/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.Date;

class AppointmentTest {

	private Date futureDate() {
		return new Date(System.currentTimeMillis() + 100000);
	}
	
	private Date pastDate() {
		return new Date(System.currentTimeMillis() - 100000);
	}
	
	// proves a fully valid appointment can be built with no exceptions
	@Test
	void testValidAppointmentCreation() {
		Appointment a = new Appointment("1234567890", futureDate(), "AV Dispatch stand up");
		assertEquals("1234567890", a.getAppointmentID());
		assertEquals("AV Dispatch stand up", a.getDescription());
	}
	
	// appointmentID testing
	
	@Test
	void testAppointmentIDNull() {
		/*
		 * ARRANGE/ACT: try to build with a null ID
		 * ASSERT: constructor should refuse it
		 */
		assertThrows(IllegalArgumentException.class, () -> 
			new Appointment(null, futureDate(), "Sleep clinic consultation"));
	}
	
	@Test
	void testAppointmentIDExactly10Chars_Succeeds() {
		// boundary test: 10 char limit 
		Appointment a = new Appointment("1234567890", futureDate(), "Sleep clinic consultation");
		assertEquals("1234567890", a.getAppointmentID());
	}
	
	@Test
	void testAppointmentIDTooLong() {
		// boundary test: 11 chars should fail, one char over the limit of 10 chars
		assertThrows(IllegalArgumentException.class, () -> 
			new Appointment("12345678901", futureDate(), "Sleep clinic consultation"));
	}
	
	@Test
	void testAppoinmtnetIDNotUpdateable() {
		/*
		 * no setAppointmentID() confirms the ID never changes
		 * even after other fields on the same object are updated
		 */
		Appointment a = new Appointment("1234567890", futureDate(),"Sleep clinic consultation");
		a.setDescription("Follow up with sleep specialist");
		assertEquals("1234567890", a.getAppointmentID());
	}
	
	// appointmentDate testing
	
	@Test
	void testAppointmentDateNull() {
		assertThrows(IllegalArgumentException.class, () -> 
			new Appointment("1234567891", null, "CS 320 tutoring session"));
	}
	
	@Test
	void testAppointmentDateInPast() {
		// a date before now should fail per requirements
		assertThrows(IllegalArgumentException.class, () -> 
			new Appointment("1234567891", pastDate(), "CS 320 tutoring session"));
	}
	
	// description testing 
	
	@Test
	void testDescriptionNull() {
		assertThrows(IllegalArgumentException.class, () -> 
			new Appointment("1234567892", futureDate(), null));
	}
	
	@Test
	void testDescriptionExactly50Chars_Succeeds() {
		// boundary test: 50 char limit
		String description50 = "Wedding venue walkthrough with planner and vendors".substring(0,50);
		Appointment a = new Appointment("1234567892", futureDate(), description50);
		assertEquals(50, a.getDescription().length());
	}
	
	@Test
	void testDescriptionTooLong() {
		// 51 chars, one over the limit of 50
		String tooLong = "Wedding venue walkthrough with planner and vendors.";
		assertThrows(IllegalArgumentException.class, () -> 
			new Appointment("1234567892", futureDate(), tooLong));
	}
	
}



















