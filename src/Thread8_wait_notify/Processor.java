package Thread8_wait_notify;

import java.util.Scanner;

public class Processor {
	public void producer() throws InterruptedException {
		synchronized (this) {
			System.out.println("Producer thread running ....");
			wait();
			System.out.println("Resumed.");
		}
	}

	public void consumer() throws InterruptedException {

		Scanner scanner = new Scanner(System.in);
		// Ensure producer lock first
		Thread.sleep(200);

		synchronized (this) {
			System.out.println("Waiting for return key.");
			scanner.nextLine();
			System.out.println("Return key pressed.");
			notify();
			Thread.sleep(5000);
		}
	}
}
