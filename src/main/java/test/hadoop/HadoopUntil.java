package test.hadoop;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

public class HadoopUntil {

    FileSystem	fs=null;
    
    @Before
	public void init() throws IOException, InterruptedException, URISyntaxException {
		Configuration conf=new Configuration();
		fs=FileSystem.get(new URI("hdfs://weekend110:9000/"),conf,"hadoop");
		
	}
	
    @Test
    public void upload() throws IllegalArgumentException, IOException {
    	fs.copyFromLocalFile(new Path("C:\\Users\\DELL\\Downloads\\数码通-员工录用通知函-陈峰.pdf"), new Path("/cftmp"));
    }
    
    @Test
	public void download() throws IllegalArgumentException, IOException {
		fs.copyToLocalFile(false,new Path("hdfs://weekend110:9000/cftmp/core-site.xml"), new Path("F:\\tmp\\20180626.xml"),true);
	}
}
