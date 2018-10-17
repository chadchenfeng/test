package cf.designpatterns.decoratorpattern;

public class HeroImpl implements Hero {

	private String name;
	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public HeroImpl(String name) {
		super();
		this.name = name;
	}



	@Override
	public void init() {
		System.out.println("光膀子一个的"+this.name);
	}

}
