package hadoop.mr.wordcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WCRunner {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		//Configuration conf=new Configuration();
		/*FileSystem fs=FileSystem.get(conf);*/
		//conf.set("fs.defaultFS", "file:///");
		Job job = Job.getInstance();
		//job.setJarByClass(WCRunner.class);
		job.setMapperClass(WCMapper.class);
		job.setReducerClass(WCReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		
		//FileInputFormat.setInputPaths(job, new Path("/wc/srcdata"));
		FileInputFormat.setInputPaths(job, new Path("F:\\tmp\\trade_info.txt"));
		
		/*if(fs.exists(new Path("/wc/output"))) {
			fs.delete(new Path("/wc/output"));
		}*/
		
		//FileOutputFormat.setOutputPath(job, new Path("/wc/output"));
		FileOutputFormat.setOutputPath(job, new Path("F:\\tmp\\output"));
		
		job.waitForCompletion(true);
		
	}
}
