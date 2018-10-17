package cf.designpatterns.decoratorpattern;

public abstract class HeroDecorator implements Hero {

	private Hero heroDecorator;
	
	
	public HeroDecorator(Hero heroDecorator) {
		super();
		this.heroDecorator = heroDecorator;
	}



	@Override
	public void init() {
		heroDecorator.init();

	}

}
