package richword;

public enum PdfFontSizeEnum {
	TITLE1(16),SUBTITLE1(14),TITLE2(14),SUBTITLE2(12),TABLETITLE(12),TABLEFONT(10);
	private int size;
	private PdfFontSizeEnum(int size) {
		this.size=size;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
