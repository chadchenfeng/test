package hadoop.mr.areapartion;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import hadoop.mr.flowcount.FlowBean;

public class FlowSumArea {

	public static class FlowSumAreaMapper extends Mapper<LongWritable, Text, FlowBean, NullWritable>{

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
	
	public static class FlowSumAreaReducer extends Reducer<FlowBean, NullWritable, FlowBean, NullWritable>{

		@Override
		protected void reduce(FlowBean key, Iterable<NullWritable> arg1,
				Reducer<FlowBean, NullWritable, FlowBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			context.write(key, NullWritable.get());
		}
		
	}
	public static  class AreaPartioner<KEY, VALUE> extends Partitioner<KEY, VALUE>{

		private static HashMap<String,Integer> areaMap=new HashMap<String,Integer>();
		static {
			areaMap.put("126",0);
			areaMap.put("163", 1);
		}
		@Override
		public int getPartition(KEY key, VALUE value, int numPartitions) {
			int index=key.toString().indexOf("@");
			String mapKey=key.toString().substring(index+1, index+4);
			int areaCode=areaMap.get(mapKey);
			
			return areaCode;
		}
		
	}
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance();
		
		job.setMapperClass(FlowSumAreaMapper.class);
		job.setReducerClass(FlowSumAreaReducer.class);
		job.setPartitionerClass(AreaPartioner.class);
		job.setNumReduceTasks(2);
		job.setJarByClass(FlowSumArea.class);
		job.setMapOutputKeyClass(FlowBean.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setOutputKeyClass(FlowBean.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("F:\\tmp\\trade_info.txt"));
		FileOutputFormat.setOutputPath(job, new Path("F:\\tmp\\FlowSumAreaOutput1"));
		
		System.exit(job.waitForCompletion(true)?0:1);
	}
}
