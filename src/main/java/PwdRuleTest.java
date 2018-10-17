
public class PwdRuleTest {

	public static void main(String[] args) {
		String pwd="1111@kl;'sdfghjkl;/aaaaaaa";
		System.out.println(matchPwdNew(pwd));
	}
	
	public static boolean matchPwd(String newPwd) {
		int i = newPwd.matches(".*\\d+.*") ? 1 : 0;
		int j = newPwd.matches(".*[a-zA-Z]+.*") ? 1 : 0;
		int k = newPwd.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;
		int l = newPwd.length();
		if (i + j + k < 2 || l < 6 || l > 20 ) {
			return false;
		}
		return true;
	}
	
	public static boolean matchPwdNew(String pwd) {
		String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![~!@#$%^&*()_+|<>,.?/:;'\\\\[\\\\]{}\\\"]).{6,20}$";	
		
		return pwd.matches(regex);
	}
}
