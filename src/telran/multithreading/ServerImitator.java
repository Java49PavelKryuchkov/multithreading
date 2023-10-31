package telran.multithreading;

import java.util.concurrent.*;

public class ServerImitator extends Thread {
BlockingQueue<Request> queue;
ExecutorService threadPool = Executors.newFixedThreadPool(100);
	public ServerImitator(BlockingQueue<Request> queue) {
	this.queue = queue;
}
	@Override
	public void run() {
		while(true) {
			try {
				Request request = queue.take();
				threadPool.execute(request);
			} catch (InterruptedException e) {
				Request someRequest = null;
				while((someRequest = queue.poll()) != null) {
					threadPool.execute(someRequest);
				}
			}
			break;
		}
	}
	public void shutdown() throws InterruptedException {
		threadPool.awaitTermination(20,TimeUnit.SECONDS);
		threadPool.shutdownNow();
	}
}
