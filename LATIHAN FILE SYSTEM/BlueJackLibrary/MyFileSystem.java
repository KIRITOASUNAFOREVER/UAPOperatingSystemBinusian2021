package nachos.proj1;

import nachos.machine.FileSystem;
import nachos.machine.Machine;
import nachos.machine.OpenFile;

public class MyFileSystem {
	private FileSystem sistemFile = Machine.stubFileSystem();
	
	public MyFileSystem(){
		
	}
	
	public void write(String isiFile){
		OpenFile file = sistemFile.open("task.txt", true);
		byte[] buffer = isiFile.getBytes();
		file.write(buffer, 0, buffer.length);
		file.close();
	}
	
	public String read(){
		OpenFile file = sistemFile.open("task.txt", false);
		if(file == null){
			return null;
		}
		
		byte[] buffer = new byte[999];
		file.read(buffer, 0, buffer.length);
		String hasil = new String(buffer);
		file.close();
		
		return hasil;
	}
	
	public void append(String task){
		String dataLama = read().trim();
		if(!dataLama.equals("")){
			dataLama = dataLama + '\n';
		}
		dataLama = dataLama + task;
		this.write(dataLama);
	}
	
	public void eraseFile(){
		OpenFile file = sistemFile.open("task.txt", false);
		byte[] buffer = "".getBytes();
		file.write(buffer, 0, buffer.length);
		file.close();
	}
}
