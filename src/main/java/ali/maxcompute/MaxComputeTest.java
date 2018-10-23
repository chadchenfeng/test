package ali.maxcompute;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.task.SQLTask;

public class MaxComputeTest {

	public static void main(String[] args) {
		String accessId="LTAI0eQiSjb820gn";
		String accessKey ="iFSBMEs5vTOD8ADOI9LrQTTFURv9gg";
		String project = "gdt_dw";
		String endPoint = "http://service.odps.aliyun.com/api";
		String sql="select * from hemalog_actionlog2017 limit 20000;";
		AliyunAccount aliyunAccount = new AliyunAccount(accessId, accessKey);
		Odps odps = new Odps(aliyunAccount);
		odps.setEndpoint(endPoint);
		odps.setDefaultProject(project);
		
		try {
			FileOutputStream fout=new FileOutputStream("C:\\Users\\DELL\\Downloads\\t.txt");
			DataOutputStream fd=new DataOutputStream(fout);
			Instance run = SQLTask.run(odps, sql);
			run.waitForSuccess();
			Iterator<Record> resultSet = SQLTask.getResultSet(run);
			while(resultSet.hasNext()) {
				Record next = resultSet.next();
				System.out.println(next.getString("openid"));
				fd.writeUTF(next.getString("openid")+"\n");
			}
			fd.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
