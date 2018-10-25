import java.util.ArrayList;
import java.util.List;

/*
 * 解析sql中的查询字段
 */
public class ParsingSql {

	public static void main(String[] args) {
		String sql="select a.create_time --创建日期 " + 
				"	,a.scan_pv  --pv " + 
				"	,a.scan_user_uv  ---uv " + 
				"	,a.lotter_rate  ---------抽奖日期\r\n" + 
				"	,a.average_cost\r\n" + 
				"	,a.day_new_user_scan_users_proportion --新用户扫码占比\r\n" + 
				"	,a.day_old_user_scan_users_proportion \r\n" + 
				"from tmp_polardb_huangwei_ywym_gdm_daily a \r\n" + 
				"where a.type='1' \r\n" + 
				"	and a.create_time>= TO_DATE('${startDate}', 'yyyymmdd')\r\n" + 
				"	and a.create_time<= TO_DATE('${endDate}', 'yyyymmdd') \r\n" + 
				"    ORDER  by a.create_time limit 100000;";
		String reportheads=null;
		String sqlcolumns=null;
		parase(sql,reportheads,sqlcolumns);
	}
	
	public static void parase(String sql,String reportheads,String sqlcolumns) {
		String column=sql.split("from")[0].replace("select ", "");
		String[] columns = column.split(",");
		List<String> headlist=new ArrayList<String>();
		List<String> columnlist=new ArrayList<String>();
		
		for(int i=0;i<columns.length;i++) {
			String data=columns[i].trim();
			if(data.contains("--")) {
				data=data.replaceFirst("--", "==");
				data=data.replace("-", "");
				columnlist.add(data.split("==")[0].trim());
				headlist.add(data.split("==")[1].trim());
			}else {
				columnlist.add(data);
				headlist.add(data);
			}
		}
		reportheads=String.join(",",headlist);
		sqlcolumns=String.join(",", columnlist);
		System.out.println("head:"+reportheads);
		System.out.println("columns:"+sqlcolumns);
	}
}
