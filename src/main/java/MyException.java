
public class MyException extends Throwable{

	public MyException() {
		
	}
	public MyException(String error) {
		
	}
	
	public static void main(String[] args) throws MyException{
		//throw new RuntimeException("测试异常");
		throw new MyException();
	}
}
