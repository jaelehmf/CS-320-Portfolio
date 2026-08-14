/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TaskTest {

	// proves a fully valid tasks can be built with no exceptions
	@Test
	void testValidTaskCreation() {
		Task t = new Task("1234567890", "AV Dispatch shift", "0400-1600 12 hour shifts Sunday through Wednesday");
		assertEquals("1234567890", t.getTaskID());
		assertEquals("AV Dispatch shift", t.getName());
		assertEquals("0400-1600 12 hour shifts Sunday through Wednesday", t.getDescription());
	}
	
	// taskID testing
	
	@Test
	void testTaskIDNull() {
		/*
		 * ARRANGE/ACT: try to build with a null ID
		 * ASSERT: constructor should refuse it 
		 */
		assertThrows(IllegalArgumentException.class, () ->
			new Task(null, "Improve sleep", "Sleep 2200 and wake up 0300"));
	}
	
	@Test
	void testTaskIDExactly10Chars_Succeeds() {
		// boundary test: 10 char limit
		Task t = new Task("1234567890", "Improve sleep", "Sleep 2200 and wake up 0300");
		assertEquals("1234567890", t.getTaskID());
	}
	
	@Test
	void testTaskIDTooLong() {
		// boundary test: 11 chars should fail, one char over the limit of 10 chars
		assertThrows(IllegalArgumentException.class, () ->
			new Task("12345678901", "Improve sleep", "Sleep 2200 and wake up 0300"));		
	}
	
	@Test
	void testTaskIDNotUpdateable() {
		/*
		 *  no setTaskID() confirms the ID never changes 
		 *  even after other fields on the same object are updated
		*/
		Task t = new Task("1234567890", "Improve sleep", "Sleep 2200 and wake up 0300");
		t.setName("Get more sleep");
		assertEquals("1234567890", t.getTaskID());
	}
	
	// name testing 
	
	@Test 
	void testNameNull() {
		assertThrows(IllegalArgumentException.class, () ->
			new Task("1234567891", null, "0400-1600 12 hour shifts Sunday through Wednesday"));
	}
	
	@Test
	void testNameExactly20Chars_Succeeds() {
		// boundary test: confirm that a 20 char string is accepted
		String name20 = "12345678901234567890".substring(0, 20);
		Task t = new Task ("1234567891", name20, "0400-1600 12 hour shifts Sunday through Wednesday");
		assertEquals(20, t.getName().length());
	}
	
	@Test
	void testNameTooLong() {
		// 21 chars, one over the limit of 20
		String tooLong = "123456789012345678901";
		assertThrows(IllegalArgumentException.class, () ->
			new Task("1234567891", tooLong, "0400-1600 12 hour shifts Sunday through Wednesday"));
	}
	
	// description testing
	
	@Test
	void testDescriptionNull() {
		assertThrows(IllegalArgumentException.class, () ->
			new Task("1234567892", "Study CS 320", null));
	}
	
	@Test 
	void testDescriptionExactly50Chars_Succeeds(){
		// boundary test: 50 char limit
		String descript50 = "12345678901234567890123456789012345678901234567890".substring(0, 50);
		Task t = new Task("1234567892", "Study CS 320", descript50);
		assertEquals(50, t.getDescription().length());
	}
	
	@Test
	void testDescriptionTooLong() {
		// 51 chars, one over the limit of 50
		String tooLong = "123456789012345678901234567890123456789012345678901";
		assertThrows(IllegalArgumentException.class, () ->
			new Task("1234567892", "Study CS 320", tooLong));
	}

}





















