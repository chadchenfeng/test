import java.io.FileInputStream;
import java.io.FileOutputStream;

public class IOteset {

	public static void main(String[] args) throws Exception {
		FileOutputStream fout=new FileOutputStream("iotest.txt");
		fout.write(25555);
		fout.close();
		FileInputStream fin=new FileInputStream("iotest.txt");
		int read = fin.read();
		System.out.println(read);
		fin.close();
	}
}
