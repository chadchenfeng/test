package richword;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParaseDataFromCsv {

	public static void main(String[] args) throws Exception {
		Map<String, Object> tableData = new ParaseDataFromCsv().tableData("F:/tmp/dataworks/月复盘数据_20181105.csv");
		System.out.println(tableData);
		
		Map<String, Object> chartData = new ParaseDataFromCsv().chartData("F:/tmp/dataworks/月复盘数据_20181105.csv");
		System.out.println(chartData);
	}
	/*
	 * word表格数据
	 * 下单情况（获取每个组次最大月份数据）：order_information： product_name,order_quantity,shipment,landing_quantity,estimated_landing_quantity
	 * 扫码情况：scan_code_information:product_name,scan_pv,scan_uv,scan_codes
	 * 扫码转化率及单UV成本:conversion_rate_and_uv_cost:product_name,conversion_rate,uv_cost
	 * 数据结构：
	 * tablevalue:{
			order_information:[
				{product_name:"",order_quantity:"",...},{}
			],
			scan_code_information:[
			],
			conversion_rate_and_uv_cost:[
			],
			max_month:"",
			online_product_name:{}
		}
	 */
	public Map<String,Object> tableData(String filepath) throws Exception {
		InputStream in =new FileInputStream(filepath);
		BufferedReader read=new BufferedReader(new InputStreamReader(in,"gbk"));
		String line=null;
		Map<String,Object> tableValue=new HashMap<String,Object>();
		List<Map<String,String>> order_information_list=new ArrayList<>();
		List<Map<String,String>> scan_code_information_list=new ArrayList<>();
		List<Map<String,String>> conversion_rate_and_uv_cost_list=new ArrayList<>();
		String max_month=null;
		Set<String> online_product_name=new HashSet<String>();
		read.readLine();//读取第一行头部数据，以防下面读取
		while(null!=(line=read.readLine())) {
			String[] cells = line.split(",");
			String month=cells[0];
			String product_name=cells[2];
			
			String order_quantity=cells[3];
			String shipment=cells[4];
			String landing_quantity=cells[6];
			String estimated_landing_quantity=cells[14];
			
			String scan_pv=cells[7];
			String scan_uv=cells[9];
			String scan_codes=cells[11];
			
			String conversion_rate=cells[15];
			String uv_cost=cells[18];
			//下单情况 数据
			if(order_information_list.size()>0) {
				boolean flag=false;
				for(int i=0;i<order_information_list.size();i++) {
					Map<String, String> map = order_information_list.get(i);
					if(product_name.equals(map.get("product_name"))&& (month.compareTo(max_month)>=0)) {
						map.put("order_quantity", order_quantity);
						map.put("shipment", shipment);
						map.put("landing_quantity", landing_quantity);
						map.put("estimated_landing_quantity", estimated_landing_quantity);
						flag=true;
					}
				}
				if(!flag && (month.compareTo(max_month)>=0)) {
					//表示这是另一个品项
					Map<String,String> order_information_map_tmp=new HashMap<String,String>();
					order_information_map_tmp.put("product_name", product_name);
					order_information_map_tmp.put("order_quantity", order_quantity);
					order_information_map_tmp.put("shipment", shipment);
					order_information_map_tmp.put("landing_quantity", landing_quantity);
					order_information_map_tmp.put("estimated_landing_quantity", estimated_landing_quantity);
					order_information_list.add(order_information_map_tmp);
				}
			}else {
				Map<String,String> order_information_map_tmp=new HashMap<String,String>();
				order_information_map_tmp.put("product_name", product_name);
				order_information_map_tmp.put("order_quantity", order_quantity);
				order_information_map_tmp.put("shipment", shipment);
				order_information_map_tmp.put("landing_quantity", landing_quantity);
				order_information_map_tmp.put("estimated_landing_quantity", estimated_landing_quantity);
				order_information_list.add(order_information_map_tmp);
			}
			
			//扫码情况 数据
			if(scan_code_information_list.size()>0) {
				boolean flag=false;
				for(int i=0;i<scan_code_information_list.size();i++) {
					Map<String, String> map = scan_code_information_list.get(i);
					if(product_name.equals(map.get("product_name"))&& (month.compareTo(max_month)>=0)) {
						map.put("scan_pv", scan_pv);
						map.put("scan_uv", scan_uv);
						map.put("scan_codes", scan_codes);
						flag=true;
					}
				}
				if(!flag && (month.compareTo(max_month)>=0) ) {
					Map<String,String> scan_code_information_map_tmp=new HashMap<String,String>();
					scan_code_information_map_tmp.put("product_name", product_name);
					scan_code_information_map_tmp.put("scan_pv", scan_pv);
					scan_code_information_map_tmp.put("scan_uv", scan_uv);
					scan_code_information_map_tmp.put("scan_codes", scan_codes);
					scan_code_information_list.add(scan_code_information_map_tmp);
				}
			}else {
				Map<String,String> scan_code_information_map_tmp=new HashMap<String,String>();
				scan_code_information_map_tmp.put("product_name", product_name);
				scan_code_information_map_tmp.put("scan_pv", scan_pv);
				scan_code_information_map_tmp.put("scan_uv", scan_uv);
				scan_code_information_map_tmp.put("scan_codes", scan_codes);
				scan_code_information_list.add(scan_code_information_map_tmp);
			}
			
			//扫码转化率及单UV成本 数据
			if(conversion_rate_and_uv_cost_list.size()>0) {
				boolean flag=false;
				for(int i=0;i<conversion_rate_and_uv_cost_list.size();i++) {
					Map<String, String> map = conversion_rate_and_uv_cost_list.get(i);
					if(product_name.equals(map.get("product_name"))&& (month.compareTo(max_month)>=0)) {
						map.put("conversion_rate", conversion_rate);
						map.put("uv_cost", uv_cost);
						flag=true;
					}
				}
				if(!flag && (month.compareTo(max_month)>=0)) {
					Map<String,String> conversion_rate_and_uv_cost_map_tmp=new HashMap<String,String>();
					conversion_rate_and_uv_cost_map_tmp.put("product_name", product_name);
					conversion_rate_and_uv_cost_map_tmp.put("conversion_rate", conversion_rate);
					conversion_rate_and_uv_cost_map_tmp.put("uv_cost", uv_cost);
					conversion_rate_and_uv_cost_list.add(conversion_rate_and_uv_cost_map_tmp);
				}
			}else {
				Map<String,String> conversion_rate_and_uv_cost_map_tmp=new HashMap<String,String>();
				conversion_rate_and_uv_cost_map_tmp.put("product_name", product_name);
				conversion_rate_and_uv_cost_map_tmp.put("conversion_rate", conversion_rate);
				conversion_rate_and_uv_cost_map_tmp.put("uv_cost", uv_cost);
				conversion_rate_and_uv_cost_list.add(conversion_rate_and_uv_cost_map_tmp);
			}
			if(max_month==null || (month.compareTo(max_month)>0)) {
				max_month=month;
			}
			online_product_name.add(product_name);
		}
		tableValue.put("order_information", order_information_list);
		tableValue.put("scan_code_information", scan_code_information_list);
		tableValue.put("conversion_rate_and_uv_cost", conversion_rate_and_uv_cost_list);
		tableValue.put("max_month", max_month);
		tableValue.put("online_product_name", online_product_name);
		read.close();
		in.close();
		return tableValue;
	}
	
	/*
	 * 制作图表需要的数据
	 * data 传入的数据格式：
	 * {
			scan_pv_chart:{
						categorytype1:{x_label:["201805","201806","201807"],value:["2","8","6"]}
						,categorytype2:{x_label:["201805","201806","201807"],value:["18","15","10"]}
				},
			scan_uv_chart:{},
			scan_codes_chart：{},
			conversion_rate_chart：{},
			uv_cost_chart：{}
		}
	 */
	public Map<String,Object> chartData(String filepath) throws Exception {
		InputStream in =new FileInputStream(filepath);
		BufferedReader read=new BufferedReader(new InputStreamReader(in,"gbk"));
		String line=null;
		Map<String,Object> chartData=new HashMap<String,Object>();
		Map<String,Map<String,List<String>>> scan_pv_chart_map=new HashMap<>();
		Map<String,Map<String,List<String>>> scan_uv_chart_map=new HashMap<>();
		Map<String,Map<String,List<String>>> scan_codes_chart_map=new HashMap<>();
		Map<String,Map<String,List<String>>> conversion_rate_chart_map=new HashMap<>();
		Map<String,Map<String,List<String>>> uv_cost_chart_map=new HashMap<>();
		read.readLine();//读取第一行头部数据，以防下面读取
		while(null!=(line=read.readLine())) {
			String[] cells = line.split(",");
			String month=cells[0];
			String product_name=cells[2];
			
			String month_scan_pv=cells[8];
			String month_scan_uv=cells[10];
			String month_scan_codes=cells[12];
			String month_conversion_rate=cells[15];
			String month_uv_cost=cells[18];
			//扫码次数
			if(scan_pv_chart_map.isEmpty() || !scan_pv_chart_map.containsKey(product_name)) {
				List<String> scan_pv_chart_x_list=new ArrayList<String>();
				List<String> scan_pv_chart_y_list=new ArrayList<String>();
				scan_pv_chart_x_list.add(month);
				scan_pv_chart_y_list.add(month_scan_pv);
				Map<String,List<String>> scan_pv_xy_map=new HashMap<>();
				scan_pv_xy_map.put("x_label", scan_pv_chart_x_list);
				scan_pv_xy_map.put("value", scan_pv_chart_y_list);
				scan_pv_chart_map.put(product_name, scan_pv_xy_map);
			}else {
				for(Map.Entry<String,Map<String,List<String>>> entry:scan_pv_chart_map.entrySet()) {
					String categroy_type=entry.getKey();
					if(product_name.equals(categroy_type)) {
						Map<String, List<String>> value = entry.getValue();
						value.get("x_label").add(month);
						value.get("value").add(month_scan_pv);
					}
				}
			}
			//扫码人数
			if(scan_uv_chart_map.isEmpty() || !scan_uv_chart_map.containsKey(product_name)) {
				List<String> scan_uv_chart_x_list=new ArrayList<String>();
				List<String> scan_uv_chart_y_list=new ArrayList<String>();
				scan_uv_chart_x_list.add(month);
				scan_uv_chart_y_list.add(month_scan_uv);
				Map<String,List<String>> scan_uv_xy_map=new HashMap<>();
				scan_uv_xy_map.put("x_label", scan_uv_chart_x_list);
				scan_uv_xy_map.put("value", scan_uv_chart_y_list);
				scan_uv_chart_map.put(product_name, scan_uv_xy_map);
				
			}else {
				for(Map.Entry<String,Map<String,List<String>>> entry:scan_uv_chart_map.entrySet()) {
					String categroy_type=entry.getKey();
					if(product_name.equals(categroy_type)) {
						Map<String, List<String>> value = entry.getValue();
						value.get("x_label").add(month);
						value.get("value").add(month_scan_uv);
					}
				}
			}
			
			//扫码码数
			if(scan_codes_chart_map.isEmpty() || !scan_codes_chart_map.containsKey(product_name)) {
				List<String> scan_codes_chart_x_list=new ArrayList<String>();
				List<String> scan_codes_chart_y_list=new ArrayList<String>();
				scan_codes_chart_x_list.add(month);
				scan_codes_chart_y_list.add(month_scan_codes);
				Map<String,List<String>> scan_codes_xy_map=new HashMap<>();
				scan_codes_xy_map.put("x_label", scan_codes_chart_x_list);
				scan_codes_xy_map.put("value", scan_codes_chart_y_list);
				scan_codes_chart_map.put(product_name, scan_codes_xy_map);
			}else {
				for(Map.Entry<String,Map<String,List<String>>> entry:scan_codes_chart_map.entrySet()) {
					String categroy_type=entry.getKey();
					if(product_name.equals(categroy_type)) {
						Map<String, List<String>> value = entry.getValue();
						value.get("x_label").add(month);
						value.get("value").add(month_scan_codes);
					}
				}
			}
			//转化率
			if(conversion_rate_chart_map.isEmpty() || !conversion_rate_chart_map.containsKey(product_name)) {
				List<String> conversion_rate_chart_x_list=new ArrayList<String>();
				List<String> conversion_rate_chart_y_list=new ArrayList<String>();
				conversion_rate_chart_x_list.add(month);
				conversion_rate_chart_y_list.add(month_conversion_rate);
				Map<String,List<String>> map=new HashMap<>();
				map.put("x_label", conversion_rate_chart_x_list);
				map.put("value", conversion_rate_chart_y_list);
				conversion_rate_chart_map.put(product_name, map);
			}else {
				for(Map.Entry<String,Map<String,List<String>>> entry:conversion_rate_chart_map.entrySet()) {
					String categroy_type=entry.getKey();
					if(product_name.equals(categroy_type)) {
						Map<String, List<String>> value = entry.getValue();
						value.get("x_label").add(month);
						value.get("value").add(month_conversion_rate);
					}
				}
			}
			//单UV成本
			if(uv_cost_chart_map.isEmpty() || !uv_cost_chart_map.containsKey(product_name)) {
				List<String> uv_cost_chart_x_list=new ArrayList<String>();
				List<String> uv_cost_chart_y_list=new ArrayList<String>();
				uv_cost_chart_x_list.add(month);
				uv_cost_chart_y_list.add(month_uv_cost);
				Map<String,List<String>> map=new HashMap<>();
				map.put("x_label", uv_cost_chart_x_list);
				map.put("value", uv_cost_chart_y_list);
				uv_cost_chart_map.put(product_name, map);
			}else {
				for(Map.Entry<String,Map<String,List<String>>> entry:uv_cost_chart_map.entrySet()) {
					String categroy_type=entry.getKey();
					if(product_name.equals(categroy_type)) {
						Map<String, List<String>> value = entry.getValue();
						value.get("x_label").add(month);
						value.get("value").add(month_conversion_rate);
					}
				}
			}
			
		}
		
		chartData.put("scan_pv_chart", scan_pv_chart_map);
		chartData.put("scan_uv_chart", scan_uv_chart_map);
		chartData.put("scan_codes_chart", scan_codes_chart_map);
		chartData.put("conversion_rate_chart", conversion_rate_chart_map);
		chartData.put("uv_cost_chart", uv_cost_chart_map);
		read.close();
		in.close();
		return chartData;
	}
}
