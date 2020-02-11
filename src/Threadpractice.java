

class practice extends Thread {

	String[] st;

	practice(String[] st) {
		this.st = st;

	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		for (int i = 0; i < 15; i++) {
//			try {
				System.out.println(st[i]);
//			} 
//			catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
	}
}
}

public class Threadpractice {
	public static void main(String args[]) throws InterruptedException {
		System.out.println("start");
		String[] st = new String[100];
		for (int i = 0; i < 15; i++) {
			st[i] = i + ":번째";
		}
		practice pt = new practice(st);
		Thread[] threadArray = new Thread[5];
		for (Thread thread : threadArray) {
			thread = new Thread(new practice(st));
			thread.start();
			thread.join();
		}
		
		System.out.println("end");

	}
}
