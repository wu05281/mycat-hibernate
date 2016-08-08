package lee.singleton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonObject {

	/**
	 * 存放不同的序列对应值
	 */
	private Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();

	/**
	 * 缓存长度
	 */
	private static String seqCacheSuffix = "_SEQCACHE";

	/**
	 * 序列值
	 */
	private static String sequenceSuffix = "_SEQUENCE";

	private SingletonObject(){}

	public static SingletonObject obj = null;

	public static SingletonObject getInstance(){
		if (obj == null) {
			synchronized (SingletonObject.class) {
				if (obj == null) {
					obj = new SingletonObject();
				}
			}
		}
		return obj;
	}

	public synchronized void genSeq(String tableName){
		//缓存长度
		Integer seqCache = map.get(tableName + seqCacheSuffix);
		//序列值
		Integer sequence = map.get(tableName + sequenceSuffix);
		if (seqCache == null){
			//获取缓存长度
			seqCache = 9;
			//获取此序列初始值
			sequence = 9;
		} 
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
		String id = formatter.format(new Date()) + "" + sequence;
		System.out.println(id);
		seqCache--;
		sequence++;
		map.put(tableName + seqCacheSuffix, seqCache);
		map.put(tableName + sequenceSuffix, sequence);
	}


}
