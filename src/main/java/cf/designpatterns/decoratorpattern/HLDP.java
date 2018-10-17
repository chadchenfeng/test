package cf.designpatterns.decoratorpattern;

public class HLDP extends HeroDecorator{

	public HLDP(Hero heroDecorator) {
		super(heroDecorator);
	}

	public void init() {
		super.init();
		System.out.println("装备红莲斗篷");
	}

}
