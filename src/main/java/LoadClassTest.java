
public class LoadClassTest {

	public static void main(String[] args) {
		System.out.println(Test1.a);
	}
}

class Test1{
	public static int a=6/3;
	static {
		System.out.println("test static block");
	}
}
