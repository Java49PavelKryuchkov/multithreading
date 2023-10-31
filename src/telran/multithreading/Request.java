package telran.multithreading;

import java.util.concurrent.atomic.*;

public class Request implements Runnable {
long runningTime;

	public Request(long nRunnings) {
	this.runningTime = runningTime;
}
static AtomicInteger counter = new AtomicInteger(0);
	@Override
	public void run() {
		try {
			Thread.sleep(runningTime);
			counter.incrementAndGet();
		}catch(InterruptedException e) {
			
		}
		
	}


	public static Object getActualRequestAmount() {
		return counter.get();
	}
	}
