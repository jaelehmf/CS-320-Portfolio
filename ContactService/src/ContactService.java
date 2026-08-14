/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */

import java.util.HashMap;
import java.util.Map;

public class ContactService {
	/*
	 * HashMap gives 0(1) lookup/add/remove by ID
	 * using contactID as the key means duplicate keys cannot silently coexist
	 */

	private Map<String, Contact> contacts = new HashMap<>();
	
	public void addContact(Contact contact) {
		if (contacts.containsKey(contact.getContactID())) {
			throw new IllegalArgumentException("Contact ID already exists");
		}
		contacts.put(contact.getContactID(), contact);
	}
	
	public void updateFirstName(String contactID, String firstName) {
		getContact(contactID).setFirstName(firstName);
	}
	
	public void updateLastName(String contactID, String lastName) {
		getContact(contactID).setLastName(lastName);
	}
	
	public void updatePhone(String contactID, String phone) {
		getContact(contactID).setPhone(phone);
	}
	
	public void updateAddress(String contactID, String address) {
		getContact(contactID).setAddress(address);
	}
	
	public void deleteContact(String contactID) {
		contacts.remove(contactID);
	}
	
	private Contact getContact(String contactID) {
		Contact c = contacts.get(contactID);
		if (c == null) {
			throw new IllegalArgumentException("Contact not found");
		}
		return c;
	}
}








