/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskServiceTest {
	private TaskService service;
	
	/*
	 * runs before each tests so it starts with a clean/empty service
	 * prevents tests from accidentally depending on leftover state
	 */
	@BeforeEach
	void setUp() {
		service = new TaskService();
	}
	
	// add testing
	
	@Test
	void testAddTask_Succeeds() {
		Task t = new Task ("1111111111", "AV Dispatch shift", "0400-1600 12 hour shifts Sunday through Wednesday");
		service.addTask(t);
		/*
		 * proves it was stored by successfully updating it 
		 * after an update on a missing task would throw, so confirms it's there
		 */
		service.updateName("1111111111", "AV Dispatch shift");
	}
	
	@Test
	void testAddDuplicateTaksID_Throws() {
		Task t1 = new Task("1111111111", "AV Dispatch shift", "0400-1600 12 hour shifts Sunday through Wednesday");
		Task t2 = new Task("1111111111", "Improve sleep", "Sleep 2200 and wake up 0300");
		service.addTask(t1);
		// t2 has same ID as t1, should this should be rejected to meet unique ID requirement
		assertThrows(IllegalArgumentException.class, () ->
			service.addTask(t2));
	}
	
	// deletion testing 
	
	@Test
	void testDeleteTask_RemovesID() {
		Task t = new Task("2222222222", "Improve sleep", "Sleep 2200 and wake up 0300");
		service.addTask(t);
		service.deleteTask("2222222222");
		// after deletion, any antion that references that ID should fail
		assertThrows(IllegalArgumentException.class, () ->
			service.updateName("2222222222", "Get more sleep"));
	}
	
	@Test
	void testDeleteNonExistentTask_DoesNotThrow() {
		// deleting an ID that was never added should not crash service
		assertDoesNotThrow(() -> service.deleteTask("9999999999"));
	}
	
	// updateName testing
	
	@Test
	void testUpdateName_Succeeds() {
		Task t = new Task("3333333333", "Study CS 320", "Complete Module Four Milestone");
		service.addTask(t);
		service.updateName("3333333333", "Study CS 305");
		assertEquals("Study CS 305", t.getName());
	}
	
	@Test 
	void testUpdateName_NonExistentID_Throws() {
		assertThrows(IllegalArgumentException.class, () ->
			service.updateName("0000000000", "Study CS 305"));
	}
	
	// updateDescription testing
	
	@Test
	void testUpdateDescription_Succeeds() {
		Task t = new Task("4444444444", "Wedding Planning", "Confirm venue for ceremony and reception");
		service.addTask(t);
		service.updateDescription("4444444444", "Deposit paid, confirm final headcount");
		assertEquals("Deposit paid, confirm final headcount", t.getDescription());
	}
	
	@Test
	void testUpdateDescription_NonExistentID_Throws() {
		assertThrows(IllegalArgumentException.class, () ->
			service.updateDescription("0000000000", "Deposit paid, confirm final headcount"));
	}

}























