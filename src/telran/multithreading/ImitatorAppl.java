package telran.multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ImitatorAppl {
	private static final int N_REQUESTS = 1000000;
	private static final long RUNNING_TIME = 1;
	
	public static void main(String[] args) throws InterruptedException {
		Request request = new Request(RUNNING_TIME);
		BlockingQueue<Request> queue = new LinkedBlockingDeque<>();
		ClientImitator client = new ClientImitator(queue, RUNNING_TIME, N_REQUESTS);
		ServerImitator server = new ServerImitator(queue);
		client.start();
		server.start();
		client.join();
		server.interrupt();
		server.shutdown();
		System.out.printf("number of all requests %d\n", N_REQUESTS);
		System.out.printf("number of processed requests %d\n", Request.getActualRequestAmount());
	}

}
