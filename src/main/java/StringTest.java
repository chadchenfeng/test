import java.util.Base64;
import java.util.Scanner;

public class StringTest {

	public static void main(String[] args) {
		String a="陈锋";
		byte[] bytes=a.getBytes();
		String encString=Base64.getEncoder().encodeToString(bytes);
		System.out.println(new String(bytes)+"----"+encString+"---"+new String(Base64.getDecoder().decode(encString)));
		
		Scanner b=new Scanner(System.in);
		int i=Integer.parseInt(b.nextLine());
		System.out.println(i+"");
		
		
	}
}
