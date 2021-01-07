package nachos.proj1;

import java.util.Vector;
import nachos.machine.Machine;
import nachos.threads.KThread;

public class Main {
	private MyConsole consoleScan = new MyConsole();
	private MyFileSystem fileSaya = new MyFileSystem();
	private Vector<Task> taskList = new Vector<>();
	
	public Main() {
		mainMenu();
	}
	private void mainMenu(){
		int pilihan;
		do{
			loadData();
			consoleScan.cetakEnter("ToDo List");
			consoleScan.cetakEnter("=========");
			consoleScan.cetakEnter("1. Insert Task");
			consoleScan.cetakEnter("2. View Task");
			consoleScan.cetakEnter("3. Complete Task");
			consoleScan.cetakEnter("4. Exit");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan){
			case 1:
				insertTask();
				break;
			case 2:
				viewTask();
				break;
			case 3:
				completeTask();
				break;
			case 4:
				consoleScan.cetakEnter("Your application has been running for "+Machine.timer().getTime() / 10000000 +" second(s).");
				break;
			}
		}while(pilihan < 1 || pilihan > 4 || pilihan !=4);
	}
	
	private void loadData(){
		String data = fileSaya.read().trim();
		String dataList[];
		if(data != null){
			dataList = data.split("\n");
			taskList.clear();
			if(!dataList[0].equals("")){
				for(int i=0 ;i < dataList.length;i++){
					String taskArr[] = dataList[i].split("#");
					Task task = new Task(taskArr[0], taskArr[1], taskArr[2], taskArr[3]);
					taskList.add(task);
				}
			}
		}
	}
	
	private void insertTask(){
		String taskTitle,taskDescription,taskType,arr[];
		
		do {
			consoleScan.cetak("Input task title [5 - 15]: ");
			taskTitle = consoleScan.bacaString();
		} while (taskTitle.length() < 5 || taskTitle.length() > 15);
		
		do {
			consoleScan.cetak("Input task description [more than 1 word]: ");
			taskDescription = consoleScan.bacaString();
			arr = taskDescription.split(" ");
		} while (arr.length < 2);
		
		do {
			consoleScan.cetak("Input task type [Important | Unimportant]: ");
			taskType = consoleScan.bacaString();
		} while (!taskType.equalsIgnoreCase("Important") && !taskType.equalsIgnoreCase("Unimportant"));
		
		String isiFile = taskTitle + "#" + taskDescription + "#" + taskType + "#" + "Not Done";
		fileSaya.append(isiFile);
	}
	
	private void viewTask(){
		if(taskList.isEmpty()){
			consoleScan.cetak("No Data!");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		consoleScan.cetakEnter("Task List");
		consoleScan.cetakEnter("=========");
		consoleScan.cetakEnter("");
		int nomor = 1;
		for (Task task : taskList) {
			consoleScan.cetakEnter("No. "+(nomor++));
			consoleScan.cetakEnter("======");
			consoleScan.cetakEnter("Title       : "+task.getTaskTitle());
			consoleScan.cetakEnter("Description : "+task.getTaskDescription());
			consoleScan.cetakEnter("Type        : "+task.getTaskType());
			consoleScan.cetakEnter("Status      : "+task.getTaskStatus());
			consoleScan.cetakEnter("");
		}
		consoleScan.cetakEnter("");
		consoleScan.cetak("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	private Vector<Task> checkUncompletedTask(){
		Vector<Task> uncompletedTaskList = new Vector<>();
		for (Task task : taskList) {
			if(task.getTaskStatus().equals("Not Done")){
				uncompletedTaskList.add(task);
			}
		}
		if(uncompletedTaskList.isEmpty()){
			return null;
		}
		return uncompletedTaskList;
	}
	
	private void completeTask(){
		Vector<Task> uncompletedTaskList = checkUncompletedTask();
		int inputIndex;
		
		if(uncompletedTaskList == null){
			consoleScan.cetakEnter("There is no uncompleted task!");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		consoleScan.cetakEnter("Task List");
		consoleScan.cetakEnter("=========");
		consoleScan.cetakEnter("");
		int nomor = 1;
		for (Task task : uncompletedTaskList) {
			consoleScan.cetakEnter("No. "+(nomor++));
			consoleScan.cetakEnter("======");
			consoleScan.cetakEnter("Title       : "+task.getTaskTitle());
			consoleScan.cetakEnter("Description : "+task.getTaskDescription());
			consoleScan.cetakEnter("Type        : "+task.getTaskType());
			consoleScan.cetakEnter("Status      : "+task.getTaskStatus());
			consoleScan.cetakEnter("");
		}
		consoleScan.cetakEnter("");
		do {
			consoleScan.cetak("Input task number [1.." + uncompletedTaskList.size() + "]: ");
			inputIndex = consoleScan.bacaInteger();
		} while (inputIndex < 1 || inputIndex > uncompletedTaskList.size());
		
		for (Task task : taskList) {
			if(task == uncompletedTaskList.get(inputIndex-1)){
				new KThread(task).fork();
				break;
			}
		}
		fileSaya.eraseFile();
		insertToFile();
	}
	
	private void insertToFile(){
		String isiFile = "";
		for (Task task : taskList) {
			isiFile += task.getTaskTitle() + "#" + task.getTaskDescription() + "#" + task.getTaskType() + "#" + task.getTaskStatus() + "\n";
		}
		fileSaya.write(isiFile);
	}
}
