package telran.multithreading;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class ListOperationsJava extends Thread {
Monitor monitor;
ArrayList<Integer> list;
expData data;
Random gen = new Random();
public ListOperationsJava(Monitor monitor, ArrayList<Integer> list, expData data) {
	this.monitor = monitor;
	this.list = list;
	this.data = data;
}
public void run() {
	int nRuns = data.nOperations();
	for(int i = 0; i < nRuns; i++) {
		if(gen.nextInt(1, 100) <= data.updateProb()) {
			updateList();
		}else {
			readList();
		}
	}
	
}
private void updateList() {
	try {
		tryLock(monitor.write());
		int nUpdates = 10;
		for (int i = 0; i < nUpdates; i++) {
			list.remove(0);

		}
		for (int i = 0; i < nUpdates; i++) {
			list.add(100);

		}
	} finally {
		monitor.write().unlock();
	}
}
private void readList() {
	try {tryLock(monitor.read());
	for (int i = 0; i < 10000; i++) {
		list.get(list.size() - 1);
	}
} finally {
	monitor.read().unlock();
}
}
private void tryLock(Lock lock) {
	while(!lock.tryLock()) {
		monitor.coount().incrementAndGet();
	}
}
}
