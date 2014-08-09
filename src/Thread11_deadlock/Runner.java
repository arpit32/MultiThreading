package Thread11_deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {
	private Account acc1 = new Account();
	private Account acc2 = new Account();

	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	// Resolve deadlock
	private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
		while (true) {
			// Acquire locks
			boolean gotFirstLock = false;
			boolean gotSecondLock = false;

			try {
				gotFirstLock = firstLock.tryLock();
				gotSecondLock = secondLock.tryLock();
			} finally {
				if (gotFirstLock && gotSecondLock) {
					return;
				}
				// 把所住的释放，让别的线程有秩序的机会
				if (gotFirstLock) {
					firstLock.unlock();
				}
				// 把所住的释放，让别的线程有秩序的机会
				if (gotSecondLock) {
					secondLock.unlock();
				}
			}

			// Locks not acquired
			Thread.sleep(1);
		}
	}

	public void firstThread() throws InterruptedException {
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			// 这种方式就会造成死锁，有可能一个线程锁住了lock1，等待lock2，一个线程锁住了lock2，等待lock1.
			// 两个线程都处于等待状态没法执行。
			// lock1.lock();
			// lock2.lock();
			acquireLocks(lock1, lock2);
			try {
				Account.transfer(acc1, acc2, random.nextInt(100));
			} catch (Exception e) {
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void secondThread() throws InterruptedException {
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			// 这种方式就会造成死锁，有可能一个线程锁住了lock1，等待lock2，一个线程锁住了lock2，等待lock1.
			// 两个线程都处于等待状态没法执行。
			// lock2.lock();
			// lock1.lock();
			acquireLocks(lock2, lock1);
			try {
				Account.transfer(acc2, acc1, random.nextInt(100));
			} catch (Exception e) {
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void finished() {
		System.out.println("Account 1 balance: " + acc1.getBalance());
		System.out.println("Account 2 balance: " + acc2.getBalance());
		System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));
	}
}
