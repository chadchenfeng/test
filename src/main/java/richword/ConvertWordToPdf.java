package richword;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.xwpf.converter.core.XWPFConverterException;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/*
 * 失败，不好用，转换的pdf有问题
 */
public class ConvertWordToPdf {

	public static void main(String[] args) throws Exception {
		String docpath="F:/tmp/freemarker(2).doc";
		String pdfpath="F:/tmp/dataworks/test.pdf";
		convertDocxToPdf(docpath,pdfpath);
	}
	
	public static void convertDocxToPdf(String docPath,String pdfPath) throws XWPFConverterException, IOException {
		InputStream docstream=new FileInputStream(docPath);
		XWPFDocument doc=new XWPFDocument(docstream);
		
		OutputStream pdf=new FileOutputStream(pdfPath);
		PdfOptions pdfOption = PdfOptions.create();
		/*pdfOption.fontProvider((familyName,encoding,size,style,color)->{
			try {
				BaseFont bfChinese =BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
				com.lowagie.text.Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);
				if (familyName != null)
                    fontChinese.setFamily(familyName);
                return fontChinese;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		});*/
		ByteArrayOutputStream targetStream = null;
		PdfConverter.getInstance().convert(doc, pdf, pdfOption);
		System.out.println("转换成功");
	}
	
	
	
}
