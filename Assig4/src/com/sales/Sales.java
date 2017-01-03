package com.sales;

import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.*;

import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
//import org.apache.hadoop.


public class Sales {
	
    public static class Map_Test extends MapReduceBase implements
    Mapper<LongWritable,Text,Text,IntWritable>
    {

		@Override
		public void map(LongWritable arg0, Text value, OutputCollector<Text, IntWritable> output, Reporter arg3)
				throws IOException {
			// TODO Auto-generated method stub
			String line = value.toString();
			
			StringTokenizer stringTokenizer = new StringTokenizer(line);
			while(stringTokenizer.hasMoreTokens())
			{
				String word = stringTokenizer.nextToken();
				value.set(word);
				output.collect(value, new IntWritable(1));
				
			}
			System.out.println("Mapper1");
			
		}
    	
    	
    }
    
    public static class Reduce_Test extends MapReduceBase implements
    Reducer<Text,IntWritable,Text,IntWritable>
    {
    	

		@Override
		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output,
				Reporter arg3) throws IOException {
			// TODO Auto-generated method stub
			
			int sum=0;
			while(values.hasNext())
			{
				sum= sum +values.next().get();
			}
			output.collect(key, new IntWritable(sum));
			
		}
    	
    }

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
			JobConf config = new JobConf(Sales.class);
			
			config.setJobName("Sales");
			config.setOutputKeyClass(Text.class);
			config.setOutputValueClass(IntWritable.class);
			
			config.setMapperClass(Map_Test.class);
			config.setReducerClass(Reduce_Test.class);
			
			config.setInputFormat(TextInputFormat.class);
			config.setOutputFormat(TextOutputFormat.class);
			config.set("fs.defaultFS","hdfs://localhost:9000");
			FileInputFormat.setInputPaths(config, new Path("/hadoop/telivision.txt"));
			FileOutputFormat.setOutputPath(config,new Path("/output2/"));
		
		   JobClient.runJob(config);
	}

}
