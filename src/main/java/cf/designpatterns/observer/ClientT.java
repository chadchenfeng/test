package cf.designpatterns.observer;

public class ClientT {

	public static void main(String[] args) {
		ObserverT r1=new ObserverT("陈锋");
		ObserverT r2=new ObserverT("陈娇");
		ObserverT r3=new ObserverT("陈紫涵");
		
		ObserableT t=new ObserableT();
		t.addObserver(r1);
		t.addObserver(r2);
		t.addObserver(r3);
		t.setPaper("测试用的");
	}
}
