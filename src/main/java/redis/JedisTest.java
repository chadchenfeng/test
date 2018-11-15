package redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class JedisTest {

	public static void main(String[] args) {
		Jedis jedis=new Jedis("localhost", 6379);
		jedis.auth("cf");
//		testServerWritePerformance(jedis);
//		testServerReadPerformance(jedis);
//		testHash(jedis);
//		testList(jedis);
//		testSet(jedis);
		testZset(jedis);
	}
	
	public static void testServerWritePerformance(Jedis jedis) {
		jedis.flushDB();
		int i=0;
		long start = System.currentTimeMillis();
		while(true) {
			jedis.set("name"+i, i+"");
			i++;
			long end = System.currentTimeMillis();
			if(end-start>10000) {
				break;
			}
			
		}
		jedis.close();
		System.out.println("1s钟内操作次数："+i);
	}
	
	public static void testServerReadPerformance(Jedis jedis) {
		int i=0;
		long start = System.currentTimeMillis();
		while(true) {
			jedis.get("name"+i);
			i++;
			long end = System.currentTimeMillis();
			if(end-start>1000) {
				break;
			}
			
		}
		jedis.close();
		System.out.println("1s钟内操作次数："+i);
	}
	
	public static void testHash(Jedis jedis) {
		Map<String,String> education=new HashMap<>();
		education.put("dad", "the University");
		education.put("mom", "College");
		education.put("baby", "Uneducated");
		jedis.hmset("education", education);
		List<String> hmget = jedis.hmget("education", "dad","mom","baby");
		System.out.println(hmget.toString());
	}
	public static void testList(Jedis jedis) {
		jedis.lpush("dadskills", "java","redis","jfreechart","freemarker");
		List<String> lrange = jedis.lrange("dadskills", 0, -1);
		System.out.println(lrange.toString());
	}
	public static void testSet(Jedis jedis) {
		jedis.sadd("ihave", "czh","cj","grandfather","grandmother");
		Set<String> smembers = jedis.smembers("ihave");
		System.out.println(smembers.toString());
	}
	
	public static void testZset(Jedis jedis) {
		jedis.zadd("rankofmembers", 1, "czh");
		jedis.zadd("rankofmembers", 3, "cf");
		jedis.zadd("rankofmembers", 2, "cj");
		Set<String> zrangeByScore = jedis.zrangeByScore("rankofmembers", 1, 2);
		System.out.println(zrangeByScore.toString());
	}
}
