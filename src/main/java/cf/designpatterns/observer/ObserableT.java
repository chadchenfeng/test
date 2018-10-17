package cf.designpatterns.observer;

import java.util.Observable;

public class ObserableT extends Observable{

	private String paper;

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
		this.setChanged();
		this.notifyObservers();
	}

	
}
