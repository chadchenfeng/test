package cf.reflection;

public class UserDaoImpl implements IUserDao {

	@Override
	public void updateInfo(String userId) {
		System.out.println("更新用户"+userId+"信息");

	}

	@Override
	public String queryInfo(String userId) {
		System.out.println("查询用户"+userId+"信息");
		return userId;
	}

	@Override
	public void deleteInfo(String userId) {
		System.out.println("删除用户"+userId+"信息");

	}

}
