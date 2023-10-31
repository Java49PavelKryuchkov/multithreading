package telran.multithreading.messaging;

import java.util.concurrent.TimeUnit;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class MyLinkedBlockingQueue<E> implements MyBlockingQueue<E> {
	int limit;
	private List<E> queue = new LinkedList<>();
	private Lock monitor = new ReentrantLock();
	private Condition consumerWaitingCondition = monitor.newCondition();
	private Condition producerWaitingCondition = monitor.newCondition();
	
	public MyLinkedBlockingQueue(int limit) {
		super();
		this.limit = limit;
	}
	@Override
	public boolean add(E e) {
		try {
			monitor.lock();
			if(queue.size() == limit) {
				throw new IllegalStateException();
			}
			boolean res = queue.add(e);
			consumerWaitingCondition.signal();
			return res;
		}finally {
			monitor.unlock();
		}
		
	}

	@Override
	public boolean offer(E e) {
		boolean res = true;
		try {
			monitor.lock();
			if(queue.size() == limit) {
				res = false;
			}
			queue.add(e);
			consumerWaitingCondition.signal();
			return res;
		}finally {
			monitor.unlock();
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		try {
			monitor.lock();
			while(queue.size() == limit) {
				producerWaitingCondition.await();
			}
			queue.add(e);
			consumerWaitingCondition.signal();
		}finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		try {
			monitor.lock();
			while(queue.size() == limit) {
				if(!producerWaitingCondition.await(timeout, unit)) {
					return false;
				}
			}
			queue.add(e);
			consumerWaitingCondition.signal();
			return true;
		}finally {
			monitor.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		try {
			monitor.lock();
			while(queue.isEmpty()) {
				consumerWaitingCondition.await();
			}
			E res = queue.remove(0);
			producerWaitingCondition.signal();
			return res;
		}finally {
			monitor.unlock();
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		try {
			monitor.lock();
			while(queue.isEmpty()) {
				if(!consumerWaitingCondition.await(timeout, unit)) {
					return null;
				}
			}
			E res = queue.remove(0);
			if(res != null) {
				producerWaitingCondition.signal();
			}
			return res;
		}finally {
			monitor.unlock();
		}
	}

	@Override
	public E poll() {
		E res = null;
		try {
			monitor.lock();
			if(queue.size() != 0) {
				res = queue.remove(0);
				producerWaitingCondition.signal();
			}
		}finally {
			monitor.unlock();
		}
		return res;
	}

	@Override
	public E remove() {
		try {
			monitor.lock();
			if(queue.size() == 0) {
				throw new NoSuchElementException();
			}
			E res = queue.remove(0);
			producerWaitingCondition.signal();
			return res;
		}finally {
			monitor.unlock();
		}
	}

	@Override
	public E peek() {
		E res = null;
		try {
			monitor.lock();
			if(queue.size() != 0) {
				res = queue.get(0);
			}
			return res;
		}finally {
			monitor.unlock();
		}
	}

	@Override
	public E element() {
		E res = null;
		try {
			monitor.lock();
			if(queue.isEmpty()) {
				throw new NoSuchElementException();
			}
			res = queue.get(0);	
			return res;
		}finally {
			monitor.unlock();
		}
	}

}
