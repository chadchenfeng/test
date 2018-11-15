package richword;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class CreatePdfByPoiApi {

	public static void main(String[] args) throws Exception {
String currentday = new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("company_name", "山东伊怡");
		map.put("total", "1000");
		map.put("price", "0.05");
		map.put("product_name_list", "蓝莓优酪乳、纯牛奶、黑牛奶、鲜态度纯牛奶、每日坚果、鲜态度-中老年高钙低脂牛奶、小熊贝贝乳酸菌饮品");
		map.put("product_names", "蓝莓优酪乳、小熊贝贝乳酸菌饮品");
		map.put("num","8");
		map.put("current_date", currentday);
		map.put("max_month", "201811");
		
		List<Map<String,String>> list=new ArrayList<>();
		Map<String,String> map1=new HashMap<>();
		map1.put("product_name", "蓝莓优酪乳");
		map1.put("order_quantity", "600000");
		map1.put("shipment", "292786");
		map1.put("landing_quantity", "278919");
		map1.put("estimated_landing_quantity", "277727");
		Map<String,String> map2=new HashMap<>();
		map2.put("product_name", "纯牛奶");
		map2.put("order_quantity", "1500000");
		map2.put("shipment", "1085805");
		map2.put("landing_quantity", "975556");
		map2.put("estimated_landing_quantity", "277727");
		list.add(map1);
		list.add(map2);
		map.put("datalist", list);
		String image="F:/tmp/dataworks/单uv成本柱状图.png";
		map.put("image", image);
		String templatepath="/richword";
//		String templatename="freemarker_pdf.ftl";
		String templatename="freemarker_pdf_template.ftl";
		String temp_htmlurl="F:/tmp/dataworks/pdf_html3.html";
		String pdfUrl="F:/tmp/dataworks/pdf_test3.pdf";
		new CreatePdfByPoiApi().createHtmlBytemplate(map,templatepath,templatename,temp_htmlurl);
//		new CreatePdfByFreemarkerApi().convertHtmlToPdf(temp_htmlurl, pdfUrl);
		new CreatePdfByPoiApi().convertHtml2Pdf(temp_htmlurl, pdfUrl);
		

	}
	
	public  void createHtmlBytemplate(Map<String,Object> map,String templatePath,String templateName,String htmlUrl) {
		Configuration conf=new Configuration(Configuration.VERSION_2_3_28);
		conf.setEncoding(Locale.getDefault(), "utf-8");
		conf.setClassForTemplateLoading(this.getClass(), templatePath);
		try {
			Template template = conf.getTemplate(templateName);
			File outHtmFile = new File(htmlUrl);
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outHtmFile),"utf-8"));
			template.process(map, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void convertHtmlToPdf(String htmlUrl,String pdfUrl) throws Exception {
		File htmFile = new File(htmlUrl);
		File pdfFile = new File(pdfUrl);
		String url = htmFile.toURI().toURL().toString();
		OutputStream os = new FileOutputStream(pdfFile);
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(url);
		ITextFontResolver fontResolver = renderer.getFontResolver();
		fontResolver.addFont("F:\\tmp\\simsun.ttc,1",
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		renderer.layout();
		renderer.createPDF(os);
		os.close();

	}
	
	public void convertHtml2Pdf(String srcfile,String dest) throws DocumentException, IOException {
		Document document=new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(srcfile), Charset.forName("utf-8"));
		document.close();
	}
	
}
