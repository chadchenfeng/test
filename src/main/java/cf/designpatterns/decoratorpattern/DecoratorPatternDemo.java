package cf.designpatterns.decoratorpattern;

public class DecoratorPatternDemo {

	public static void main(String[] args) {
		Hero dw=new HeroImpl("dianwei");
		HeroDecorator hd=new BZZZ(dw);
		HeroDecorator hd2=new HLDP(hd);
		hd2.init();
	}
}
