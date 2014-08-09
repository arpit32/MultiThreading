package Thread9_low_level_producer_consumer;

import java.util.LinkedList;
import java.util.Random;

public class Processor {

	private LinkedList list = new LinkedList();
	private final int LIMIT = 10;
	private Object lock = new Object();

	public void producer() throws InterruptedException {
		int value = 0;

		while (true) {
			synchronized (lock) {
				while (list.size() == LIMIT) {
					lock.wait();
				}
				list.add(String.valueOf(value++));
				lock.notify();
			}
		}
	}

	public void consumer() throws InterruptedException {

		Random random = new Random();
		while (true) {
			synchronized (lock) {
				while (list.size() == 0) {
					lock.wait();
				}
				System.out.print("List size is: " + list.size());
				Object value = list.removeFirst();
				System.out.println("; value is: " + value);
				lock.notifyAll();
			}
			Thread.sleep(random.nextInt(1000));
		}
	}
}
