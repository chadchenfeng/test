package hadoop.mr.flowcount;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class FlowCountRunner extends Configured implements Tool{

	@Override
	public int run(String[] args) throws Exception {
//		Configuration conf=new Configuration();
//		Job job = Job.getInstance(conf);
		Job job = Job.getInstance();
		
		job.setMapperClass(FlowCountMapper.class);
		job.setReducerClass(FlowCountReducer.class);
		job.setJarByClass(FlowCountRunner.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlowBean.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		FileInputFormat.setInputPaths(job, new Path("F:\\tmp\\trade_info.txt"));
		FileOutputFormat.setOutputPath(job, new Path("F:\\tmp\\FlowCountOutput"));
		
		return job.waitForCompletion(true)?0:1;
	}
	
	public static void main(String[] args) throws Exception {
		
		int result=ToolRunner.run(new FlowCountRunner(), null);
		System.exit(result);
	}

}
