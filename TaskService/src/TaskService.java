/*
 * Author: Jeri Mabuti
 * Course: CS 320 Software Test, Automation QA
 */

import java.util.HashMap;
import java.util.Map;

public class TaskService {
	/*
	 * HashMap gives 0(1) lookup/add/remove by ID
	 * using taskID as the key means duplicate keys cannot silently coexist
	 */
	private Map<String, Task> tasks = new HashMap<>();
	
	// requires tasks with a unique ID
	public void addTask(Task task) {
		// checks if the ID is already in use before adding 
		if (tasks.containsKey(task.getTaskID())) {
			throw new IllegalArgumentException("Task ID already exists");
		}
		tasks.put(task.getTaskID(), task);
	}
	
	// ability to delete tasks using task ID
	public void deleteTask(String taskID) {
		tasks.remove(taskID);
	}
	
	// updates tasks fields using task ID
	public void updateName(String taskID, String name) {
		// getTask() checks if the ID exists
		getTask(taskID).setName(name);
	}
	
	public void updateDescription(String taskID, String description) {
		getTask(taskID).setDescription(description);
	}
	
	// private helper to ensure both update methods do not repeat the same logic
	private Task getTask(String taskID) {
		Task t = tasks.get(taskID);
		if (t == null) {
			throw new IllegalArgumentException("Task not found");
		}
		return t;

	}
	
}





















