package lee.singleton;

public class MyThread implements Runnable {

	@Override
	public void run() {
		SingletonObject obj = SingletonObject.getInstance();
		obj.genSeq("TEST");
	}

}
