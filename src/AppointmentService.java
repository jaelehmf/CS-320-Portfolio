/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */

import java.util.HashMap;
import java.util.Map;

public class AppointmentService {
	/*
	 * HashMap gives 0(1) lookup/add/remove by ID
	 * using appointmentID as the key means duplicate keys cannot silently coexist 
	 */
	private Map<String, Appointment> appointments = new HashMap<>();
			
	// requires appointments with a unique ID
	public void addAppointment(Appointment appointment) {
		if (appointments.containsKey(appointment.getAppointmentID())) {
		throw new IllegalArgumentException("Appoinmtment ID already exists");
		}
		appointments.put(appointment.getAppointmentID(), appointment);
	}
	
	//ability to delete appointments using appointment ID
	public void deleteAppointment(String appointmentID) {
		// checks if ID exists before removing it
		if (!appointments.containsKey(appointmentID)) {
			throw new IllegalArgumentException("Appointment not found");
		}
		appointments.remove(appointmentID);
	}
	
	// helper for tests; not a stated replacement but useful for verification
	public Appointment getAppointment(String appointmentID) {
		return appointments.get(appointmentID);
	}
	
}

	
























