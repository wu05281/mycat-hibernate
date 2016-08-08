package lee.singleton;

public class SignletonTest {

	public static void main(String[] args) {
		for (int i =0; i<9; i++) {
			Thread t = new Thread(new MyThread());
			t.start();
		}
	}

}
