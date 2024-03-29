package nachos.proj1;

import java.util.Vector;

import nachos.threads.KThread;
import nachos.threads.ThreadQueue;

public class MyQueue extends ThreadQueue{
	Vector<KThread> queue = new Vector<>();
	
	public MyQueue() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void waitForAccess(KThread thread) {
		queue.add(thread);
	}

	@Override
	public KThread nextThread() {
		if(queue.isEmpty()){
			return null;
		}else{
			//FIFO
			return queue.remove(0);
			
			//LIFO
			//return queue.remove(queue.size() - 1);
		}
	}

	@Override
	public void acquire(KThread thread) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}

}
