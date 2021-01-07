package nachos.proj1;

public class File implements Runnable{
	private String fileName,fileType;
	private int fileSize;
	
	public File(String fileName, String fileType, int fileSize) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public void run() {
		System.out.println("File Name : "+this.fileName);
		System.out.println("File Size : "+this.fileSize);
		System.out.println("File Type : "+this.fileType);
		System.out.println();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
