package cf.reflection;

public interface IUserDao {

	public void updateInfo(String userId);
	public String queryInfo(String userId);
	public void deleteInfo(String userId);
}
