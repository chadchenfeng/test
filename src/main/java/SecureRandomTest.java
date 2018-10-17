import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SecureRandomTest {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		SecureRandom srand=new SecureRandom();
		//SecureRandom srand=SecureRandom.getInstance("SHA1PRNG");
		long Long = srand.nextLong();
		System.out.println(String.valueOf(Long));
		
	}
	
	
	private SecretKey geneKey(String reed) throws Exception {  
	    //获取一个密钥生成器实例  
	    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");  
	     SecureRandom random = new SecureRandom();  
	    random.setSeed(reed.getBytes());//设置加密用的种子，密钥  
	    
	    keyGenerator.init(random);  
	    SecretKey secretKey = keyGenerator.generateKey();  
	    return secretKey;  
	}  

}
