package hadoop.mr.flowcount;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class FlowBean implements Writable,WritableComparable<FlowBean>{
	@Override
	public int compareTo(FlowBean o) {
		
		return sum_flow>o.getSum_flow()?-1:1;
	}

	private String email;
	private long up_flow;
	private long down_flow;
	private long sum_flow;
	
	public long getSum_flow() {
		return sum_flow;
	}

	public void setSum_flow(long sum_flow) {
		this.sum_flow = sum_flow;
	}

	public FlowBean() {
		
	}

	public FlowBean(String email, long up_flow, long down_flow) {
		super();
		this.email = email;
		this.up_flow = up_flow;
		this.down_flow = down_flow;
		this.sum_flow=up_flow+down_flow;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getUp_flow() {
		return up_flow;
	}

	public void setUp_flow(long up_flow) {
		this.up_flow = up_flow;
	}

	public long getDown_flow() {
		return down_flow;
	}

	public void setDown_flow(long down_flow) {
		this.down_flow = down_flow;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(email);
		out.writeLong(up_flow);
		out.writeLong(down_flow);
		out.writeLong(sum_flow);
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.email=in.readUTF();
		this.up_flow=in.readLong();
		this.down_flow=in.readLong();
		this.sum_flow=in.readLong();
		
	}

	@Override
	public String toString() {
		return ""+email+" up_flow=" + up_flow + ", down_flow=" + down_flow + ", sum_flow=" + sum_flow + "";
	}
	
	

}
