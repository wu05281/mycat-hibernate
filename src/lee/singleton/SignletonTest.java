package lee.singleton;

public class SignletonTest {

	public static void main(String[] args) {
		for (int i =0; i<9; i++) {
			SingletonObject obj = SingletonObject.getInstance();
			obj.genSeq("TEST");
		}
	}

}
