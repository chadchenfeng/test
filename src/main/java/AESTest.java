import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.mindrot.jbcrypt.BCrypt;


public class AESTest {

	private static final String ALGORITHM = "AES";  
    
    /** 
     * 生成密钥 
     * @return 
     * @throws Exception 
     */  
    private SecretKey geneKey(String salt) throws Exception {  
        //获取一个密钥生成器实例  
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);  
       // SecureRandom random = new SecureRandom(); 
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(salt.getBytes());//设置加密用的种子，密钥  
        System.out.println("random:"+random.nextInt());
        keyGenerator.init(128,random);  
        SecretKey secretKey = keyGenerator.generateKey();  
        return secretKey;  
    }  
      
    /** 
     * 读取存储的密钥 
     * @param keyPath 
     * @return 
     * @throws Exception 
     */  
   /* private SecretKey readKey(Path keyPath) throws Exception {  
        //读取存起来的密钥  
        byte[] keyBytes = Files.readAllBytes(keyPath);  
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);  
        return keySpec;  
    }  */
      
    /** 
     * 加密测试 
     */  
    public void testEncrypt(String salt,String content) throws Exception {  
        //1、指定算法、获取Cipher对象  
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//算法是AES  
        //2、生成/读取用于加解密的密钥  
        SecretKey secretKey = this.geneKey(salt);  
        //3、用指定的密钥初始化Cipher对象，指定是加密模式，还是解密模式  
        //cipher.init(Cipher.ENCRYPT_MODE,  new SecretKeySpec(secretKey.getEncoded(),"AES"));  
        cipher.init(Cipher.ENCRYPT_MODE,  new SecretKeySpec(salt.getBytes(),"AES"));  
        //4、更新需要加密的内容  
        //cipher.update(content.getBytes("utf-8"));  
        //5、进行最终的加解密操作  
        byte[] result = cipher.doFinal(content.getBytes("utf-8"));//加密后的字节数组  
        //byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));//加密后的字节数组  
        String base64Result = Base64.getEncoder().encodeToString(result);//对加密后的字节数组进行Base64编码  
        //System.out.println("Encrypt--base64Result---Result: " +  new String(result, "utf-8")); 
        System.out.println("Encrypt--base64Result---Result: " +  base64Result); 
        
    }  
      
    /** 
     * 解密测试 
     */  
    public void testDecrpyt(String salt,String content) throws Exception {  
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  
        SecretKey secretKey = this.geneKey(salt);  
     //   cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey.getEncoded(),"AES"));  
        cipher.init(Cipher.DECRYPT_MODE, secretKey);  
        byte[] encodedBytes = Base64.getDecoder().decode(content.getBytes("utf-8")); 
        byte[] result = cipher.doFinal(encodedBytes);//对加密后的字节数组进行解密  
        System.out.println("Result: " + new String(result,"utf-8"));  
    }  
    
    /** 
     * 将二进制转换成16进制 
     * @method parseByte2HexStr 
     * @param buf 
     * @return 
     * @throws  
     * @since v1.0 
     */  
    public static String parseByte2HexStr(byte buf[]){  
        StringBuffer sb = new StringBuffer();  
        for(int i = 0; i < buf.length; i++){  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    }  
      
    /** 
     * 将16进制转换为二进制 
     * @method parseHexStr2Byte 
     * @param hexStr 
     * @return 
     * @throws  
     * @since v1.0 
     */  
    public static byte[] parseHexStr2Byte(String hexStr){  
        if(hexStr.length() < 1)  
            return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
            result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    }  
    
    
    
    public static void main(String[] args) throws Exception {
    /*	String hashed = BCrypt.hashpw("chenfeng", BCrypt.gensalt(10));
    	
    	System.out.println(BCrypt.gensalt(10)+":"+hashed);*/
    	//System.out.println(decrpyt("[B@168a7a1","24UXnYD6x4RpT2KFE4jt/bf82Jk7gRozBlZQFVgJUWiy8VydnuCsOT5TJUK05IFN"));
    	//new AESTest().testEncrypt("+qCvO4Nh4xWd9tvOgUyICVRHDPoL1Ajhs0fAF9C4rD1L8ozReoGxO/qh+ItVBXVgeSwoUZimiGVps/LEmzVtKQ==","173260775561529573711320$2a$10$kosIGuXcXLb5Rr3iaepTzuHuq0ULLW7D.liafSE4716ls8YSyz50e");   //chen恶风111111111111
    	new AESTest().testEncrypt("1234567890000000","12345");  
    	//new AESTest().testDecrpyt("12345","Tb5/Az42KwSeo88oq8VFDw==");
    	//new AESTest().testDecrpyt("[B@168a7a1","+tlzMRQ/Qx3R0WLTVPao1mJJm6MXk6thBiH/bFAjO5ANjzK6uAv/I56zub4MAJay6j5iJjXHoKPFR3q50FPXfVCgPprwiUzemsQqjcyMVEs=");
	}
}  

