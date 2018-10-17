package hbase.test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HbaseDao {

	public static void main(String[] args) throws Exception {
	    Configuration conf = HBaseConfiguration.create();	
	    conf.set("hbase.zookeeper.quorum", "192.168.2.200:2181,weekend111:2181,weekend112:2181");
	    HBaseAdmin admin = new HBaseAdmin(conf);
	    TableName tablename=TableName.valueOf("cf");
		HTableDescriptor table = new HTableDescriptor(tablename);
		HColumnDescriptor base_info = new HColumnDescriptor("base_info");
		base_info.setMaxVersions(5);
		table.addFamily(base_info);
	    admin.createTable(table);
	}
}
