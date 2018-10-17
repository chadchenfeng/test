package cf.designpatterns;

public class FactoryTest {

	public static void main(String[] args) {

		try {
			IUserDao user = UserFactory.getUser();
			user.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
