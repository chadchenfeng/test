import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

	public static void main(String[] args) {
		String from="cf13538169542@163.com";
		String to="@qq.com";
		String host = "smtp.163.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.host", host);
		properties.put("mail.smtp.auth", true);
		Session session = Session.getDefaultInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("cf13538169542@163.com", "tree102099");
			}
			
		});
		try {
				long starttime = System.currentTimeMillis();
				String toemial="cffighter@163.com";
				MimeMessage message  = new MimeMessage(session);
				message.setFrom(from);
				message.addRecipient(RecipientType.TO, new InternetAddress(toemial));
				message.setSubject("这是一封来自程序测试动发送的邮件");
				message.setText("陌生人你好，这个是本人测试用发送的邮件，若有打扰，十分抱歉");
				
				String fileattachment="F:/tmp/dataworks/固定码_20181021.csv";
				String file2="F:/tmp/dataworks/基础数据_20181021.csv";
				String file3="F:/tmp/dataworks/老用户抽奖动作(天水长城)_20181021.csv";
				List<String> filelist=new ArrayList<String>();
				filelist.add(file2);
				filelist.add(fileattachment);
				filelist.add(file3);
				
				//发送附件
				Multipart multipart = new MimeMultipart();
				for(String f:filelist) {
					BodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(f)));
					messageBodyPart.setFileName(f.substring(f.lastIndexOf("/")+1));
					multipart.addBodyPart(messageBodyPart);
				}
				MimeBodyPart textcontent=new MimeBodyPart();
				textcontent.setText("具体报表详情见附件");
				multipart.addBodyPart(textcontent);
				
				message.setContent(multipart);
				
				Transport.send(message);
				long endtime = System.currentTimeMillis();
				System.out.println("检查下邮件是否发送成功到"+toemial+"邮箱"+" 耗时："+(endtime-starttime));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
