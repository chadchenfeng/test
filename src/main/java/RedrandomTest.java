

import java.text.DecimalFormat;

public class RedrandomTest {

	public static void main(String[] args) {
		double float_multipe=0.4;  //红包金额浮动频率
		double minimum_money=0.01; //红包最小金额
		double fee_proportion=0.2; //手续费率
		double money=10;  //红包总金额
		int rednum=100; //红包个数
		double a=redEnvRandomMoney(float_multipe,minimum_money,fee_proportion,money,rednum);
		System.out.println(a);
		
	}
	
	
	public static double redEnvRandomMoney(double float_multipe,double minimum_money,double fee_proportion, double money,int rednum) {
		if(rednum==1) {
			return money;
		}else {
			double redavg=money*(1-fee_proportion)/rednum;
			double d = Math.random() * 2*float_multipe + (1-float_multipe);
			double redMoney=(double)(Math.round(d*redavg*1000))/1000;
			double retMoney=redMoney>minimum_money?redMoney:minimum_money;
			return retMoney;
		}
	}

}
