package ali.maxcompute;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.task.SQLTask;

public class MaxComputeTest {

	public static void main(String[] args) throws OdpsException {
		String accessId="LTAI0eQiSjb820gn";
		String accessKey ="iFSBMEs5vTOD8ADOI9LrQTTFURv9gg";
		String project = "gdt_dw";
		String endPoint = "http://service.odps.aliyun.com/api";
//		String date_time=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());  //日期减7天，录上周的数据
		String date_time="20181105095510";
		String sql="INSERT INTO automated_report_extension_field(create_date,field_key,field_value ) VALUES ("+date_time+",'gross_profit_ad','140000');";
		AliyunAccount aliyunAccount = new AliyunAccount(accessId, accessKey);
		Odps odps = new Odps(aliyunAccount);
		odps.setEndpoint(endPoint);
		odps.setDefaultProject(project);
		Instance run = SQLTask.run(odps, sql);
		run.waitForSuccess();
		/*try {
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
		}*/
	}
}
