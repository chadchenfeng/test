/*
 * 汉诺塔问题
 * x y z 3个轴 ，64个汉诺塔
 * 将64个汉诺塔从x轴移到z轴
 * 将前63个汉诺塔移到y轴 x->y，再将第64个汉诺塔移到z轴 x->z
 * 将前63个汉诺塔移到z轴 y->z
 * 依次拆分......
 * 
 */
public class HanioTowerTest {

	static int count=0;
	public static void main(String[] args) {
		hanioTower(20,"x","y","z");
		System.out.println("总次数："+count);
	}
	
	public static void hanioTower(int n,String from,String buffer,String to) {
		count++;
		if(n==1) {
			System.out.println(from+"->"+to);
			return;
		}
		hanioTower(n-1,from,to,buffer);
		System.out.println(from+"->"+to);
		hanioTower(n-1,buffer,from,to);
	}
}
