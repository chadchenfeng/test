import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
			for(int i=0;i<10;i++) {
				long starttime = System.currentTimeMillis();
				int toqq=313228732;
				toqq-=i;
				String toemial=toqq+to;
				MimeMessage message  = new MimeMessage(session);
				message.setFrom(from);
				message.addRecipient(RecipientType.TO, new InternetAddress(toemial));
				message.setSubject("这是一封来自程序测试动发送的邮件");
				message.setText("陌生人你好，这个是本人测试用发送的邮件，若有打扰，十分抱歉");
				Transport.send(message);
				long endtime = System.currentTimeMillis();
				System.out.println("检查下邮件是否发送成功到"+toemial+"邮箱"+" 耗时："+(endtime-starttime));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
