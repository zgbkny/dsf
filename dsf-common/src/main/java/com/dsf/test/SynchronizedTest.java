package com.dsf.test;

public class SynchronizedTest implements Runnable {
	int i = 0;
	public static void main(String args[]) {
		SynchronizedTest test = new SynchronizedTest();
		test.i = -1;
		new Thread(test).run();
		test.i = 10;
		new Thread(test).run();
	}
	
	public void test(int i) {
		
		
		synchronized (this) {
			if (i < 0) {
				System.out.println(i);
				return;
			}
			System.out.println(i);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		test(i);
	}
}
