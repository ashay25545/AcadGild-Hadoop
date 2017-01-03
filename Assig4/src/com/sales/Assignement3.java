package com.sales;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

import com.sales.Sales.Map_Test;
import com.sales.Sales.Reduce_Test;
import org.apache.hadoop.mapred.JobClient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import org.apache.hadoop.fs.*;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;

public class Assignement3 {
	
	public static class Map extends MapReduceBase implements Mapper<LongWritable,Text,Text,Text>
	{

		@Override
		public void map(LongWritable arg0, Text val, OutputCollector<Text, Text> out, Reporter arg3)
				throws IOException {
			// TODO Auto-generated method stub
			
			String values = val.toString();
			String[] columns=values.split("|");
			if(!columns[0].equalsIgnoreCase("NA") || !columns[1].equalsIgnoreCase("NA"))
			{
				out.collect(new Text(values),new Text());
			}
			
		}

//		@Override
//		public void map(LongWritable arg0, Text val, OutputCollector<Text, IntWritable> output, Reporter arg3)
//				throws IOException {
//			// TODO Auto-generated method stub
//			try{
//				String[] values =  val.toString().split("|");
//				Configuration conf = new Configuration();
//
//     			//URI uri = new URI("hdfs://localhost:9000/");
//     			conf.set("fs.defaultFS","hdfs://localhost:9000");
//				FileSystem fs =  FileSystem.get(conf);
//				if(!values[0].equalsIgnoreCase("NA") || !values[1].equalsIgnoreCase("NA"))
//				{
//					if(!fs.exists(new Path("test1.txt")))
//					{
//						BufferedWriter bw =  new BufferedWriter(new OutputStreamWriter(fs.create(new Path("test1.txt"))));
//						bw.write(val.toString());
//						
//					}
//					else
//					{
//						BufferedWriter bw =  new BufferedWriter(new OutputStreamWriter(fs.append(new Path("test1.txt"))));
//						bw.write(val.toString());
//						
//					}
//					
////					FSDataOutputStream opStr=fs.append(new Path("/hadoop/test.txt"));
////					opStr.writeBytes(val.toString());
//					
//				}
//				fs.close();
//			}
//			catch(Exception exp)
//			{
//				exp.printStackTrace();
//			}
			
			
			
			
			
		//}
		
	}
	
	public static void main(String[] args) throws Exception
	{
		JobConf config = new JobConf(Sales.class);
		config.setMapperClass(Map.class);
		config.setNumReduceTasks(0);
		config.setOutputKeyClass(Text.class);
		config.setOutputValueClass(Text.class);
		
		config.set("fs.defaultFS","hdfs://localhost:9000");
		FileInputFormat.setInputPaths(config, new Path("/hadoop/television.txt"));
		FileOutputFormat.setOutputPath(config,new Path("/output1/"));
	    JobClient.runJob(config);
	    
		
	}

}
