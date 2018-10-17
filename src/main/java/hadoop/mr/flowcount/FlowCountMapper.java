package hadoop.mr.flowcount;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, FlowBean>.Context context)
			throws IOException, InterruptedException {
		String line=value.toString();
		String[] words = StringUtils.split(line, "\t");
		if(words.length==0) {
			return;
		}
		String email=words[0];
		long up_flow=Long.parseLong(words[1]);
		long down_flow=Long.parseLong(words[2]);
		FlowBean flowBean=new FlowBean(email,up_flow,down_flow);
		context.write(new Text(email), flowBean);
	}

	
}
