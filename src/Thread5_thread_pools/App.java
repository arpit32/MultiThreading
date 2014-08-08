package Thread5_thread_pools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {
	private int id;

	public Processor(int id) {
		this.id = id;
	}

	public void run() {
		System.out.println("Starting: " + id);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Completed: " + id);
	}
}

public class App {

	public static void main(String[] args) {
		// ExecutorService executor = Executors.newFixedThreadPool(2);
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		for (int i = 0; i < 5; i++) {
			// executor.submit(new Processor(i));
			executor.schedule(new Processor(i), 3, TimeUnit.SECONDS);
		}
		executor.shutdown();
		System.out.println("All tasks submitted.");
		try {
			// Blocks until all tasks have completed execution after a shutdown
			// request, or the timeout occurs, or the current thread is
			// interrupted, whichever happens first.
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("All tasks completed.");
	}
}