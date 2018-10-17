package cf.designpatterns.decoratorpattern;

public class BZZZ extends HeroDecorator{

	
	public BZZZ(Hero heroDecorator) {
		super(heroDecorator);
	}
	
	public void init() {
		super.init();
		System.out.println("装备霸者重装");
	}

}
