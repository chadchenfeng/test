import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MD5Test {
	private static InputStream inputstream;



	public static void main(String[] args) throws Exception {
		/*MessageDigest md5=MessageDigest.getInstance("SHA-256");
		
		inputstream = new FileInputStream("E:\\工作文档\\数码通\\返乐多\\邀请模块\\邀请表结构设计及逻辑设计.txt"); 
		byte[] buffer=new byte[1024];
		int length=0;
		while((length=inputstream.read(buffer))>0) {
			md5.update(buffer, 0, length);
		};
		
		byte[] result=md5.digest();
		
		System.out.print(toHexString(result));*/
		String a="陈锋";
		System.out.println(Bit32(a));
		System.out.println(Bit16(a));
	}
	
	public static byte[] strEncryption(String text,String type) {
		try {
			MessageDigest md5=MessageDigest.getInstance(type);
			byte[] retBytes=md5.digest(text.getBytes());
			//return toHexString(retBytes);
			return retBytes;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
/*	public static String toHexString(byte[] byteArray) {
		  if (byteArray == null || byteArray.length < 1)
		   throw new IllegalArgumentException("this byteArray must not be null or empty");
		 
		  final StringBuilder hexString = new StringBuilder();
		  for (int i = 0; i < byteArray.length; i++) {
		   if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
		    hexString.append("0");
		   hexString.append(Integer.toHexString(0xFF & byteArray[i]));
		  }
		  return hexString.toString().toLowerCase();
		 }*/

	public static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }
	
	
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
	  
	private static String toHexString(byte[] b) {  
	    StringBuilder sb = new StringBuilder(b.length * 2);  
	    for (int i = 0; i < b.length; i++) {  
	        sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);  
	        sb.append(HEX_DIGITS[b[i] & 0x0f]);  
	    }  
	    return sb.toString();  
	}  
	  
	public static String Bit32(String SourceString) throws Exception {  
	    MessageDigest digest = java.security.MessageDigest.getInstance("MD5");  
	    digest.update(SourceString.getBytes());  
	    byte messageDigest[] = digest.digest();  
	    return toHexString(messageDigest);  
	}  
	  
	public static String Bit16(String SourceString) throws Exception {  
	    return Bit32(SourceString).substring(8, 24);  
	}  

}
