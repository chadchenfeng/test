package hadoop.mr.flowcount;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FlowCountReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

	@Override
	protected void reduce(Text key, Iterable<FlowBean> values, Reducer<Text, FlowBean, Text, FlowBean>.Context context)
			throws IOException, InterruptedException {
		
		long upFlow=0;
		long downFlow=0;
		for(FlowBean value:values) {
			upFlow+=value.getUp_flow();
			downFlow+=value.getDown_flow();
		}
		
		context.write(key, new FlowBean(key.toString(),upFlow,downFlow));
	}

	
}
