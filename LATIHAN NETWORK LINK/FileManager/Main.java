package nachos.proj1;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class Main {
	private MyConsole consoleScan = new MyConsole();
	private MyNetworkLink netLink = new MyNetworkLink();
	
	public Main() {
		mainMenu();
	}
	private void mainMenu(){
		int pilihan;
		do{
			consoleScan.cetakEnter("Computer No: " + netLink.getAddress());
			System.out.println();
			consoleScan.cetakEnter("File Manager");
			consoleScan.cetakEnter("1. Send File");
			consoleScan.cetakEnter("2. View Existing File(s)");
			consoleScan.cetakEnter("3. Exit");
			consoleScan.cetakEnter("Choose: ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan){
			case 1:
				sendFile();
				break;
			case 2:
				viewFile();
				break;
			case 3:
				consoleScan.cetakEnter("Ticks of time: "+Machine.timer().getTime());
				break;
			}
		}while(pilihan < 1 || pilihan > 3 || pilihan !=3);
	}
	
	private void viewFile() {
		if(netLink.getFiles().isEmpty()){
			consoleScan.cetakEnter("There is no available file.");
			consoleScan.bacaString();
			return;
		}else{
			System.out.println("My File(s)");
			System.out.println("==========");
			System.out.println();
			while(!netLink.getFiles().isEmpty()){
				new KThread(netLink.getFiles().remove(0)).fork();;
			}
		}
	}
	
	private void sendFile(){
		String fileName,fileType;
		int fileSize,destinasiKomputer;
		
		do{
			consoleScan.cetak("Enter file name [must contain '.']: ");
			fileName = consoleScan.bacaString();
		}while(!fileName.contains(".") || fileName.startsWith(".") || fileName.endsWith("."));
		
		do{
			consoleScan.cetak("Enter file size [1 - 500 MB]: ");
			fileSize = consoleScan.bacaInteger();
		}while(fileSize < 1 || fileSize > 500);
		
		do{
			consoleScan.cetak("Enter file type [Word Document | Excel Worksheet | Unspecified File] (case sensitive): ");
			fileType = consoleScan.bacaString();
		}while(!fileType.equals("Word Document") && !fileType.equals("Excel Worksheet") && !fileType.equals("Unspecified File"));
		
		consoleScan.cetak("Send to computer: ");
		destinasiKomputer = consoleScan.bacaInteger();
		
		File file = new File(fileName, fileType, fileSize);
		netLink.sendFile(file, destinasiKomputer);
		consoleScan.cetakEnter("File sent!");
	}
}
