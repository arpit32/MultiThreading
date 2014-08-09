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
				// ����ס���ͷţ��ñ���߳�������Ļ���
				if (gotFirstLock) {
					firstLock.unlock();
				}
				// ����ס���ͷţ��ñ���߳�������Ļ���
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
			// ���ַ�ʽ�ͻ�����������п���һ���߳���ס��lock1���ȴ�lock2��һ���߳���ס��lock2���ȴ�lock1.
			// �����̶߳����ڵȴ�״̬û��ִ�С�
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
			// ���ַ�ʽ�ͻ�����������п���һ���߳���ס��lock1���ȴ�lock2��һ���߳���ס��lock2���ȴ�lock1.
			// �����̶߳����ڵȴ�״̬û��ִ�С�
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
