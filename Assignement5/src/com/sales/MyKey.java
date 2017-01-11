package com.sales;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;



public class MyKey implements WritableComparable<MyKey>
{
	private Text company;
	private Text product;
	
 public MyKey()
 {
	 this.company=new Text();
	 this.product=new Text();
 }
 @Override
 public String toString() {
     return this.company + " " + this.product;
 }
	
  public void setMyKey(Text company,Text product) {
		// TODO Auto-generated constructor stub
		this.company=company;
		this.product=product;
	}
	@Override
	public void readFields(DataInput dip) throws IOException {
		// TODO Auto-generated method stub
		company.readFields(dip);
		product.readFields(dip);
		
	}

	@Override
	public void write(DataOutput dop) throws IOException {
		// TODO Auto-generated method stub
		company.write(dop);
		product.write(dop);
		
	}

	
	
	@Override
	public int hashCode()
	{
		return this.company.hashCode();
	}
	@Override
	public int compareTo(MyKey o) {
		int cmp=this.company.compareTo(o.company);
		if(cmp!=0)
		{
			return cmp;
		}
		return this.product.compareTo(o.product);
	}
	
	
}
