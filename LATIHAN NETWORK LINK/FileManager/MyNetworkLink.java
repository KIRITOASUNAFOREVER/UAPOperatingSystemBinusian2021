package nachos.proj1;
import java.util.Vector;
import nachos.machine.Machine;
import nachos.machine.MalformedPacketException;
import nachos.machine.NetworkLink;
import nachos.machine.Packet;
import nachos.threads.Semaphore;

public class MyNetworkLink {
	private NetworkLink netLink = Machine.networkLink();
	private Semaphore sem = new Semaphore(0);
	private Vector<File> files = new Vector<>();
	
	public MyNetworkLink() {
		//terima paket
		Runnable receiveInterruptHandler = new Runnable() {
			
			@Override
			public void run() {
				Packet packet = netLink.receive();
				String terimaFile = new String(packet.contents);
				
				String pemisahIsiFile[] = terimaFile.split("@");
				File file = new File(pemisahIsiFile[0], pemisahIsiFile[1], Integer.parseInt(pemisahIsiFile[2]));
				files.add(file);
				System.out.println("\nNew file received: "+pemisahIsiFile[0]);
			}
		};
		
		//kirim paket
		Runnable sendInterruptHandler = new Runnable() {
			
			@Override
			public void run() {
				sem.V();
			}
		};
		netLink.setInterruptHandlers(receiveInterruptHandler, sendInterruptHandler);
	}
	
	public void sendFile(File file, int destinasiKomputer){
		String isiFile = file.getFileName() + "@" + file.getFileType() + "@" + file.getFileSize();
		try {
			Packet packet = new Packet(destinasiKomputer, this.getAddress(), isiFile.getBytes());
			netLink.send(packet);
			sem.P();
		} catch (MalformedPacketException e) {
			e.printStackTrace();
		}
	}
	
	public int getAddress(){
		return netLink.getLinkAddress();
	}

	public Vector<File> getFiles() {
		return files;
	}

	public void setFiles(Vector<File> files) {
		this.files = files;
	}
}
