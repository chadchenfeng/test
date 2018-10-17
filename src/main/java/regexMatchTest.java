import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class regexMatchTest {

	public static void main(String[] args) throws IOException {
		//String _regex="^(?![0-9]+$)(?![a-zA-Z]+$)(?![~!@#$%^&*()_+|<>,.?/:;'\\\\[\\\\]{}\\\"])[0-9A-Za-z~!@#$%^&*()_+|<>,.?/:;'\\\\\\\\\\\\\\\\[\\\\\\\\\\\\\\\\]{}\\\\\\\\\\\\\\\"]{6,20}$";
		//String _regex="^(?![0-9]+$)(?![a-zA-Z]+$)(?![~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\\\"])[0-9a-zA-Z~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\\\"]{6,20}$";
		//String _regex="^(?![0-9]+$)(?![a-zA-Z]+$)(?![~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\\\"]+$)[0-9a-zA-Z~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\\\"]{6,20}$";
		String _regex="^(?![0-9]+$)(?![a-zA-Z]+$)(?![-~!@#$%^&*()_+|<>,.?/:;=`'\\[\\]{}\\\"\\\\]+$)[0-9a-zA-Z-~!@#$%^&*()_+|<>,.?/:;=`'\\[\\]{}\\\"\\\\]{6,20}$";
		String pwd="12311\\";
		/*FileReader fr=new FileReader("F:\\tmp\\1.txt");
		BufferedReader br=new BufferedReader(fr);
		String regex=br.readLine();
		System.out.println(regex);*/
		System.out.println(_regex);
		
		System.out.println(pwd.matches(_regex));
	}
}
