package hadoop.mr.inverseindex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import hadoop.mr.flowcount.FlowBean;
import hadoop.mr.flowsort.FlowSortRunner;
import hadoop.mr.flowsort.FlowSortRunner.FlowSortMapper;
import hadoop.mr.flowsort.FlowSortRunner.FlowSortReducer;

public class InverseIndex {
	
	public static class InverseIndexMapper extends Mapper<LongWritable, Text, Text, Text>{

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String line=value.toString();
			String[] words=line.split(" ");
			FileSplit inputSplit = (FileSplit) context.getInputSplit();
			String fileName=inputSplit.getPath().getName();
			for(String word:words) {
				context.write(new Text(word), new Text(fileName));
			}
		}
	}
	
	public static class InverseIndexReducer extends Reducer<Text, Text, Text, Text>{

		@Override
		protected void reduce(Text key, Iterable<Text> value, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			TreeMap<String,Integer> fileMap=new TreeMap<String,Integer>();
			for(Text file:value) {
				String filename=file.toString();
				if(fileMap.containsKey(filename)) {
					int plusCount=fileMap.get(filename)+1;
					fileMap.put(filename, plusCount);
				}else {
					fileMap.put(filename, 1);
				}
				
			}
			StringBuilder outputStr=new StringBuilder();
			for(String mapKey:fileMap.keySet()) {
				outputStr.append(","+mapKey+":"+fileMap.get(mapKey));
			}
			context.write(key, new Text(outputStr.substring(1)));
		}
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance();
		
		job.setMapperClass(InverseIndexMapper.class);
		job.setReducerClass(InverseIndexReducer.class);
		job.setJarByClass(InverseIndex.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, new Path("F:\\tmp\\hadoopTest"));
		FileOutputFormat.setOutputPath(job, new Path("F:\\tmp\\InverseIndexOutput1"));
		
		System.exit(job.waitForCompletion(true)?0:1);
	}

}
