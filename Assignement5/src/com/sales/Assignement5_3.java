package com.sales;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;



public class Assignement5_3 {

	public static class Map_Test extends MapReduceBase implements
    Mapper<LongWritable,Text,MyKey,IntWritable>
    {
		Text company = new Text();
		Text product = new Text();

		@Override
		public void map(LongWritable arg0, Text value, OutputCollector<MyKey, IntWritable> output, Reporter arg3)
				throws IOException {
			// TODO Auto-generated method stub
			String line = value.toString();
			String[] columns = line.split("\\|");
			MyKey mk = new MyKey();
			company.set(columns[0]);
			product.set(columns[1]);
			mk.setMyKey(company, product);
			output.collect(mk, new IntWritable(1));
			
		}
    	
    	
    }
    
    public static class Reduce_Test extends MapReduceBase implements
    Reducer<MyKey,IntWritable,Text,IntWritable>
    {
    	

		@Override
		public void reduce(MyKey key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output,
				Reporter arg3) throws IOException {
			// TODO Auto-generated method stub
			MyKey mkk = new MyKey();
			Text tt = new Text();
			int sum=0;
			while(values.hasNext())
			{
				sum= sum +values.next().get();
			}
			tt.set(key.toString());
			output.collect(tt, new IntWritable(sum));
			
		}
    	
    }

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
			JobConf config = new JobConf(Assignement5_3.class);
			
			config.setJobName("Sales");
			config.setOutputKeyClass(Text.class);
			config.setOutputValueClass(IntWritable.class);
			
			config.setMapperClass(Map_Test.class);
			config.setReducerClass(Reduce_Test.class);
			
			//Used Combiner The logic will be same as reducer
			config.setCombinerClass(Reduce_Test.class);
			
			
			config.setInputFormat(TextInputFormat.class);
			config.setOutputFormat(TextOutputFormat.class);
			config.set("fs.defaultFS","hdfs://localhost:9000");
			FileInputFormat.setInputPaths(config, new Path("/output4.1/part-00000"));
			FileOutputFormat.setOutputPath(config,new Path("/output5.3333/"));
		
		   JobClient.runJob(config);
	}

}
