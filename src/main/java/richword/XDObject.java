package richword;

import java.io.Serializable;

public class XDObject implements Serializable{

	private static final long serialVersionUID = -3429302421058366696L;
	private String product;
	private String number;
	private String count;
	private String cccount;
	private String gjcount;
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getCccount() {
		return cccount;
	}
	public void setCccount(String cccount) {
		this.cccount = cccount;
	}
	public String getGjcount() {
		return gjcount;
	}
	public void setGjcount(String gjcount) {
		this.gjcount = gjcount;
	}
	
	
}
