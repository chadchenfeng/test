package hadoop.mr.flowsort;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import hadoop.mr.flowcount.FlowBean;
import hadoop.mr.flowcount.FlowCountMapper;
import hadoop.mr.flowcount.FlowCountReducer;
import hadoop.mr.flowcount.FlowCountRunner;

public class FlowSortRunner {

	public static class FlowSortMapper extends Mapper<LongWritable, Text, FlowBean, NullWritable>{

		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, FlowBean, NullWritable>.Context context)
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
			context.write(flowBean, NullWritable.get());
		}
	}
	
	
	public static class FlowSortReducer extends Reducer<FlowBean, NullWritable, FlowBean, NullWritable>{

		@Override
		protected void reduce(FlowBean key, Iterable<NullWritable> values,
				Reducer<FlowBean, NullWritable, FlowBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			context.write(key, NullWritable.get());
		}
		 
		
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance();
		
		job.setMapperClass(FlowSortMapper.class);
		job.setReducerClass(FlowSortReducer.class);
		job.setJarByClass(FlowSortRunner.class);
		job.setMapOutputKeyClass(FlowBean.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setOutputKeyClass(FlowBean.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("F:\\tmp\\trade_info.txt"));
		FileOutputFormat.setOutputPath(job, new Path("F:\\tmp\\FlowSortOutput1"));
		
		System.exit(job.waitForCompletion(true)?0:1);
	}
}
