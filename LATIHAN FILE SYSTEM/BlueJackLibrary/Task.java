package nachos.proj1;

public class Task implements Runnable{
	private String taskTitle;
	private String taskDescription;
	private String taskType;
	private String taskStatus;
	
	public Task(String taskTitle, String taskDescription, String taskType, String taskStatus) {
		super();
		this.taskTitle = taskTitle;
		this.taskDescription = taskDescription;
		this.taskType = taskType;
		this.taskStatus = taskStatus;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Override
	public void run() {
		this.taskStatus = "Done";
		System.out.println("Task with title: "+this.taskTitle+" has been mark as 'Done'");
	}
	
}
