package Thread7_producer_consumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

	private static BlockingQueue queue = new ArrayBlockingQueue(10);

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					producer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					consumer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();
	}

	private static void producer() throws InterruptedException {
		Random random = new Random();
		while (true) {
			queue.put(String.valueOf(random.nextInt(100)));
			System.out.println("Added a item.");
			Thread.sleep(1000);
		}
	}

	private static void consumer() throws InterruptedException {
		Random random = new Random();
		while (true) {
			Thread.sleep(1000);
			if (random.nextInt(10) > 4) {
				String value = (String) queue.take();
				System.out.println("Taken value: " + value
						+ "; Queue size is: " + queue.size());
			}
		}
	}
}
