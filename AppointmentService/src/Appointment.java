/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */

import java.util.Date;

public class Appointment {
	// final = once in the constructor, this can never change (makes ID immutable)
	private final String appointmentID;
	private Date appointmentDate;
	private String description;
	
	/*
	 * constructor runs when an Appointment is created
	 * validation up front means an invalid Appointment object can never exist
	 */
	public Appointment(String appointmentID, Date appointmentDate, String description) {
		/*
		 * appointmentID has no setter, so it's only validated here
		 * this is what enforces "not updateable" per requirements
		 */
		if (appointmentID == null || appointmentID.length() > 10) {
			throw new IllegalArgumentException("Invalid appointment ID");
		}
		this.appointmentID = appointmentID;
			
		// reuse the setters fot the other fields so validation logic stays in one place
		setAppointmentDate(appointmentDate);
		setDescription(description);
	}
		
	public void setAppointmentDate(Date appointmentDate) {
		//required (not null) and cannot be in the past
		if (appointmentDate == null || appointmentDate.before(new Date())) {
			throw new IllegalArgumentException("Invalid appointment date");
		}
		this.appointmentDate = appointmentDate;
	}
	
	public void setDescription(String description) {
		// required (not null) and no more than 50 chars
		if (description == null || description.length() > 50) {
			throw new IllegalArgumentException("Invalid description");
		}
		this.description = description;
	}

			
	public String getAppointmentID() {return appointmentID;}
	public Date getAppointmentDate() {return appointmentDate;}
	public String getDescription() {return description;}
}











