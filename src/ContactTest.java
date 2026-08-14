/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

	// test proving a full valid Contact can be built with no exceptions
	@Test
	void testValidContactCreation() {
		Contact c = new Contact("1234567890", "Jeri", "Mabuti", "1234567890", "123 Home Dr");
		assertEquals("1234567890", c.getContactID());
		assertEquals("Jeri", c.getFirstName());
		assertEquals("Mabuti", c.getLastName());
		assertEquals("1234567890", c.getPhone());
		assertEquals("123 Home Dr", c.getAddress());
	}
	
	// contactID testing
	
	@Test
	void testContactIDNull() {
		/*
		 * ARRANGE/ACT: try to build with a null ID
		 * ASSERT: constructor should refuse it
		 */
		assertThrows(IllegalArgumentException.class, () ->
			new Contact(null, "Jeri", "Mabuti", "1234567890", "123 Home Dr"));
	}
	
	@Test
	void testContactIDExactly10Chars_Succeeds() {
		Contact c = new Contact("1234567890", "Jeri", "Mabuti", "1234567890", "123 Home Dr");
		assertEquals("1234567890", c.getContactID());
	}
	
	@Test
	void testContactIDTooLong() {
		// boundary test: one character lover limit (11 chars) will fail
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("12345678901", "Jeri", "Mabuti", "1234567890", "123 Home Dr"));
	}
	
	@Test
	void testContactIDNotUpdateable() {
		/*
		 * no setContactID()
		 * test confirms that by proving the ID never changes after other fields are updated
		 */
		Contact c = new Contact("1234567890", "Jeri", "Mabuti", "1234567890", "123 Home Dr");
		c.setFirstName("Jae");
		assertEquals("1234567890", c.getContactID());
	}
	
	// firstName testing
	
	@Test
	void testFirstNameNull() {
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("1234567890", null, "Mabuti", "1234567890", "123 Home Dr"));
	}
	
	@Test
	void testFirstNameExactly10Chars_Succeeds() {
		Contact c = new Contact("1234567890", "1234567890", "Mabuti", "1234567890", "123 Home Dr");
		assertEquals("1234567890", c.getFirstName());
	}
	@Test
	void testFirstNameTooLong() {
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("1234567890", "JericaCharisse", "Mabuti", "1234567890", "123 Home Dr"));
	}
	
	// lastName testing
	
	@Test
	void testLastNameNull() {
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("1234567890", "Jeri", null, "1234567890", "123 Home Dr"));
	}
	
	@Test
	void testLastNameExactly10Chars_Succeeds() {
		Contact c = new Contact("1234567890", "Jeri", "1234567890", "1234567890", "123 Home Dr");
		assertEquals("1234567890", c.getLastName());
	}
	@Test
	void testLastNameTooLong() {
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("1234567890", "Jeri", "DelaCruzMabuti", "1234567890", "123 Home Dr"));
	}
	
	// phone testing
	
	@Test
	void testPhoneNull() {
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("1234567890", "Jeri", "Mabuti", null, "123 Home Dr"));
	}
	
	@Test
	void testPhoneTooShort() {
		// 9 digits in phone string, one short of required exact 10
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("1234567890", "Jeri", "Mabuti", "123456789", "123 Home Dr"));
	}
	
	@Test 
	void testPhoneTooLong() {
		// 11 digits in phone string, one over the required exact 10
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("1234567890", "Jeri", "Mabuti", "12345678901", "123 Home Dr"));
	}
	
	@Test 
	void testPhoneNonDigitChars() {
		// exactly 10 chars, but not all digits. should fail
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("1234567890", "Jeri", "Mabuti", "123-456-79", "123 Home Dr"));
	}
	
	// address testing 
	
	@Test
	void testAddressNull() {
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("1234567890", "Jeri", "Mabuti", "1234567890", null));
	}
	
	@Test
	void testAddressExactly30Chars_Succeeds() {
		// exactly 30 chars should be allowed
		String address30 = "123456789012345678901234567890";
		String exact30 = address30.substring(0, 30);
		Contact c = new Contact("1234567890", "Jeri", "Mabuti", "1234567890", exact30);
		assertEquals(exact30, c.getAddress());
	}
	
	@Test
	void testAddressTooLong() {
		// 31 chars, one over limit. should fail
		String tooLong = "1234567890123456789012345678901";
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("1234567890", "Jeri", "Mabuti", "1234567890", tooLong));
	}
}









