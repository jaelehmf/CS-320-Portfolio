/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


class ContactServiceTest {
	
	private ContactService service;
	
	/**
	 * runs before every test so each test starts with a clean/empty service
	 * prevents tests from accidentally depending on each other's leftover state
	 */
	@BeforeEach
	void setUp() {
		service = new ContactService();
	}
	
	// add
	@Test
	void testAddContact_Succeeds() {
		Contact c = new Contact("1111111111", "Jeri", "Mabuti", "1234567890", "123 Home Dr");
		service.addContact(c);
		/*
		 * prove it was actually stored by successfully updating after 
		 * an update on a missing contact would throw, so this confirms it's there
		 */
		service.updateFirstName("1111111111", "Jeri");
	}
	
	@Test
	void testAddDuplicateContactID_Throws() {
		Contact c1 = new Contact("1111111111", "Jeri", "Mabuti", "1234567890", "123 Home Dr");
		Contact c2 = new Contact("1111111111", "Fiancee", "Maiden", "0987654321", "456 Cozy St");
		service.addContact(c1);
		// c2 has same ID as c1, should be rejected to keep IDs unique
		assertThrows(IllegalArgumentException.class, () ->
			service.addContact(c2));
	}
	
	// delete
	
	@Test
	void testDeleteContact_RemovesID() {
		Contact c = new Contact("2222222222", "Jeri", "Mabuti", "1234567890", "123 Home Dr");
		service.addContact(c);
		service.deleteContact("2222222222");
		// after deletion, any action that references that ID should fail
		assertThrows(IllegalArgumentException.class, () -> 
			service.updateFirstName("2222222222", "Fiancee"));
	}
	
	@Test
	void testDeleteNonExistentContact_DoesNotThrow() {
		// deleting an ID that was never added should not crash the service
		assertDoesNotThrow(() -> service.deleteContact("9999999999"));
	}
	
	// update firstName
	
	@Test
	void testUpdateFirstName_Succeeds() {
		Contact c = new Contact("3333333333", "Jeri", "Mabuti", "1234567890", "123 Home Dr");
		service.addContact(c);
		service.updateFirstName("3333333333", "Fiancee");
		assertEquals("Fiancee", c.getFirstName());
	}
	
	@Test
	void testUpdateFirstName_NonExistentID_Throws() {
		assertThrows(IllegalArgumentException.class, () -> 
			service.updateFirstName("0000000000", "Fiancee"));	
	}
	
	// update lastName 
	
	@Test
	void testUpdateLastName_Succeeds() {
		Contact c = new Contact("4444444444", "Jeri", "Mabuti", "1234567890", "123 Home Dr");
		service.addContact(c);
		service.updateLastName("4444444444", "Maiden");
		assertEquals("Maiden", c.getLastName());	
	}
	
	@Test
	void testUpdateLastName_NonExistentID_Throws() {
		assertThrows(IllegalArgumentException.class, () -> 
			service.updateLastName("0000000000", "Maiden"));
	}
	
	// update phone
	
	@Test
	void testUpdatePhone_Succeeds() {
		Contact c = new Contact("5555555555", "Jeri", "Mabuti", "1234567890", "123 Home Dr");
		service.addContact(c);
		service.updatePhone("5555555555", "0987654321");
		assertEquals("0987654321", c.getPhone());
	}
	@Test
	void testUpdatePhone_NonExistentID_Throws() {
		assertThrows(IllegalArgumentException.class, () -> 
			service.updatePhone("0000000000", "0987654321"));
	}
	
	// update address
	
	@Test
	void testUpdateAddress_Succeeds() {
		Contact c = new Contact("6666666666", "Jeri", "Mabuti", "1234567890", "123 Home Dr");
		service.addContact(c);
		service.updateAddress("6666666666", "456 Cozy St");
		assertEquals("456 Cozy St", c.getAddress());
	}
	@Test
	void testUpdateAddress_NonExistentID_Throws() {
		assertThrows(IllegalArgumentException.class, () -> 
			service.updateAddress("0000000000", "456 Cozy St"));
	}
	
}
























