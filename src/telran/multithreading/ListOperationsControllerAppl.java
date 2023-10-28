package telran.multithreading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ListOperationsControllerAppl {
	private static final int N_NUMBERS = 1000;
	private static final int UPDATE_PROB = 0;
	private static final int N_OPERATIONS = 1000;
	private static final int N_THREADS = 100;
	
	public static void main(String[] args) throws InterruptedException {
		expData data = new expData(UPDATE_PROB, N_OPERATIONS);
		AtomicInteger count = new AtomicInteger(0);
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		Lock writeLock = lock.writeLock();
		Lock readLock = lock.readLock();
		Integer[] array = new Integer[N_NUMBERS];
		Arrays.fill(array, 100);
		ArrayList<Integer> list = new ArrayList<>(Arrays.asList(array));
		Monitor monitor = new Monitor(readLock, writeLock, count);
		ListOperationsJava[] listOperations = new ListOperationsJava[N_THREADS];
		for(int i = 0; i < listOperations.length; i++) {
			listOperations[i] = new ListOperationsJava(monitor, list, data);
			listOperations[i].start();
		}
		for(ListOperationsJava listOp: listOperations) {
			listOp.join();
		}
		System.out.println(" count of waiting iterations is " + count);
	}
	
	
	
}
