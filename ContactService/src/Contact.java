/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */

public class Contact {
	// final = once set in the constructor, this can never change (makes ID immutable)
	private final String contactID;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	
	public Contact(String contactID, String firstName, String lastName, String phone, String address) { 
		/* 
		 * contactID has no setter, so it's validated only here 
		 * this is what enforces "not updateable" per the requirements
		 */
		if (contactID == null || contactID.length() > 10) {
			throw new IllegalArgumentException("Invalid contact ID");
		}
		this.contactID = contactID;
		
		// reuse the setters so validation logic lives in only one place
		setFirstName(firstName);
		setLastName(lastName);
		setPhone(phone);
		setAddress(address);
	}
	
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.length() > 10) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		if (lastName == null || lastName.length() > 10) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}
	
	public void setPhone(String phone) {
		/* 
		 * length 10 AND every character is a digit (0-9) per requirement 
		 * \\d{10} is a regex meaning "10 digits in a row. nothing else"
		 */
		if (phone == null || !phone.matches("\\d{10}")) {
			throw new IllegalArgumentException("Invalid phone number");
		}
		this.phone = phone;
	}
	
	public void setAddress(String address) {
		if (address == null || address.length() > 30) {
			throw new IllegalArgumentException("Invalid address");
		}
		this.address = address;
	}
	
	public String getContactID() {return contactID;}
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
	public String getPhone() {return phone;}
	public String getAddress() {return address;}
	
}






