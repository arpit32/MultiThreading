package Thread13_Callable_Future;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();

		Future future = executor.submit(new Callable() {

			public Object call() throws Exception {
				Random random = new Random();
				int duration = random.nextInt(4000);

				if (duration > 2000) {
					throw new IOException("Sleeping for too long.");
				}

				System.out.println("Starting...");

				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Finished.");

				return String.valueOf(duration);
			}

		});
		// �̳߳ص�shutdown()�������÷����������̳߳صĹر����У�������shutdown()��������̳߳ز��ٽ��������񣬵��Ὣ��ǰ�������ύ����ִ����ɡ����̳߳��е���������ִ����ɺ󣬳��������̶߳���������
		executor.shutdown();

		try {
			System.out.println("Result is: " + future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			System.out.println(e);
		}
	}

}
