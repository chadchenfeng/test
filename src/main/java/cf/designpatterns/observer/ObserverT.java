package cf.designpatterns.observer;

import java.util.Observable;
import java.util.Observer;

public class ObserverT implements Observer{

	private String name;
	
	public ObserverT(String n) {
		this.name=n;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void update(Observable o, Object arg) {

		System.out.println(name+"观察者观察到的动态"+ ((ObserableT) o).getPaper());
	}

	
}
