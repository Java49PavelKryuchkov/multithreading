package telran.multithreading;

import java.util.concurrent.*;

public class ClientImitator extends Thread {
BlockingQueue<Request> queue;
long runningTime;
int nRunnings;


public ClientImitator(BlockingQueue<Request> queue, long runningTime, int nRunnings) {
	super();
	this.queue = queue;
	this.runningTime = runningTime;
	this.nRunnings = nRunnings;
}


@Override
public void run() {
		for(int i = 0; i < nRunnings; i++) {
			try {
			queue.put(new Request(runningTime));
		}catch(InterruptedException e) {
		break;
	}
	
}

}}