import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FindfileFromJar {

	public static void main(String[] args) throws IOException {
		searchFile("E:\\work\\hardwork\\gradle-2.14.1","jdbc.xml");

	}

	public static void searchFile(String dir,String targetfile) throws IOException {
		File d=new File(dir);
		if(!d.isDirectory()) {
			System.out.println("不是目录");
			return;
		}
		
		File[] files=d.listFiles();
		for(int i=0;i<files.length;i++) {
			if(files[i].isDirectory()) {
				//System.out.println(files[i].getAbsolutePath());
				searchFile(files[i].getAbsolutePath(),targetfile);
			}else {
				String filename=files[i].getAbsolutePath();
				//System.out.println(filename);
				if(filename.endsWith(".zip") || filename.endsWith(".jar")) {
					ZipFile zipfile=new ZipFile(filename);
					Enumeration<? extends ZipEntry> zipentrys=zipfile.entries();
					while(zipentrys.hasMoreElements()) {
						ZipEntry entry=zipentrys.nextElement();
						String entryname=entry.getName();
						if(entryname.contains(targetfile)) {
							System.out.println("=============="+filename+"==============");
							//FileInputStream fs=new FileInputStream(filename);
							/*JarInputStream fs= new JarInputStream(new FileInputStream(filename));
							InputStreamReader ir=new InputStreamReader(fs, "utf-8");
							//FileReader fr=new FileReader(filename);
							BufferedReader br=new BufferedReader(ir);
							String content=br.readLine();
							while(null!=content) {
								if(content.contains("BoneCPDataSource")) {
									System.out.println("=============="+filename+"==============");
									break;
								}
								content=br.readLine();
							}
							br.close();
							ir.close();
							fs.close();*/
						}
					}
					zipfile.close();
				}
				
			}
		}
	}
}
