package lee;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.persister.entity.AbstractEntityPersister;

/**
 * 自定义生成主键单例类
 * @author wubo
 *
 */
public class KeySingleton {
	
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

	private KeySingleton(){
		
	}
	
	private static KeySingleton keySingleton;
	
	public static KeySingleton getInstance(){
		if (keySingleton == null) {    
            synchronized (KeySingleton.class) {    
               if (keySingleton == null) {    
            	   keySingleton = new KeySingleton();   
               }    
            }    
        }    
		return keySingleton;
	}
	
	public Long sequence(SessionImplementor arg0, Object arg1){
		Session session = arg0.getFactory().openSession();
		Transaction tx = session.beginTransaction();
		//持久化对象  
		AbstractEntityPersister classMetadata =  (AbstractEntityPersister) arg0.getFactory().getClassMetadata(arg1.getClass());
		String tableName = classMetadata.getTableName();//表名
		//缓存长度
		Integer seqCache = map.get(tableName + seqCacheSuffix);
		//序列值
		Integer sequence = map.get(tableName + sequenceSuffix);
		if (seqCache == null){
			SQLQuery query = session.createSQLQuery("SELECT mycat_seq_nextval('"+tableName +"')");
			String seq = (String) query.uniqueResult();
			String[] seqs = seq.split(",");
			//获取缓存长度
			seqCache = Integer.valueOf(seqs[1]);
			//获取此序列初始值
			sequence = Integer.valueOf(seqs[0]);
		} 
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
		String id = formatter.format(new Date()) + "" + sequence;
		System.out.println(id);
		seqCache--;
		sequence++;
		map.put(tableName + seqCacheSuffix, seqCache);
		map.put(tableName + sequenceSuffix, sequence);
		tx.commit();
		session.close();
		return Long.valueOf(id);
	}
}
