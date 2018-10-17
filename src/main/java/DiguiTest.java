import java.io.IOException;
import java.util.TreeMap;

//利用递归反向输出输入的内容，如输入：123  输出：321
public class DiguiTest {

	public static void main(String[] args) {
		printStr("1234567890");
	}
	
	public static void print() throws IOException {
		int i=System.in.read();
		if(i!=35) print();  //"#"
		System.out.print((char)i);
		
	}
	
	public static void printStr(String a) {
		if(a.length()==1) {
			System.out.print(a);
			return;
		}
		printStr(a.substring(1));
		System.out.print(a.substring(0,1));
	}
}
