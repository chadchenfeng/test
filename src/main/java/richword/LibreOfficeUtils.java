package richword;

public class LibreOfficeUtils {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
        convertOffice2PDF("F:\\tmp\\dataworks\\山东1.docx","F:\\tmp\\dataworks\\");
        long end = System.currentTimeMillis();
        System.out.println((end-start) +"ms");
	}
	
	 /**
     * 
     * soffice --headless --invisible --convert-to pdf:writer_pdf_Export D:/test/fb35e7d25afaf1b5d34f7bdb4f830c8c.doc --outdir D:/testfile2html
     * 
     * @param srcPath
     * @param desPath
     * @return
     * @throws NullPointerException 
     */
    public static boolean convertOffice2PDF(String srcPath, String desPath) throws NullPointerException{

        System.out.println("开始进行转化.....");
        if(srcPath == null || desPath == null){
            throw new NullPointerException("转化文件不存在或者路径有问题");
        }
        String command = "";
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows")) {
            command = "cmd /c cd D:/Program Files/LibreOffice/program && D: && soffice --headless --invisible --convert-to pdf:writer_pdf_Export " + srcPath + " --outdir " + desPath;
        }
        System.err.println(command + " : 转化中....");

        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            System.out.println("转换结束");
            return true;
        } catch (Exception e) {
        	System.out.println("转换出问题");
            e.printStackTrace();
            return false;
        } finally {
            System.out.println("转化结束...");
        }
    }
}
