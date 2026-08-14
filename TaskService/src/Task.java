/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */


public class Task {
	// final = once set in the constructor, this can never change (makes ID immutable)
	private final String taskID;
	private String name;
	private String description;
	
	/*
	 * constructor runs when a Task is created
	 * validation up front means an invalid Task object can never exist
	 */
	public Task(String taskID, String name, String description) {
		/*
		 * taskID has no setter, so it's only validated here
		 * this is what enforces "not updateable" per requirements
		 */
		if (taskID == null || taskID.length() > 10) {
			throw new IllegalArgumentException("Invalid task ID");
		}
		this.taskID = taskID;
		
		// reuse the setters for the other fields so validation logic lives in one place
		setName(name);
		setDescription(description);
	}
	
	public void setName(String name) {
		// required (not null) and no more than 20 chars
		if (name == null || name.length() > 20) {
			throw new IllegalArgumentException("Invalid name");
		}
		this.name = name;
	}
	
	public void setDescription(String description) {
		// required (not null) and no more than 50 chars
		if (description == null || description.length() > 50) {
			throw new IllegalArgumentException("Invalid description");
		}
		this.description = description;
	}
	
	/*
	 * getters to return the current valud
	 * no validation needed since fields can only be set through the validated setters
	 */
	public String getTaskID() {return taskID;}
	public String getName() {return name;}
	public String getDescription() {return description;}
	
}
















