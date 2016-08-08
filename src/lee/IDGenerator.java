package lee;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.persister.entity.AbstractEntityPersister;
/**
 * 
 * @GeneratedValue(generator = "system-uuid") 
 * @GenericGenerator(name = "system-uuid", strategy = "lee.IDGenerator") 
 * 查询计数器，如果＝＝0，则表示第一次进入程序或者当前序列已用完，需重新连接数据库获取新的序列；a，获取序列，base值＋缓冲，计数器＋1；
 * 如果计数器！＝0，则表示序列的缓冲值还没有用完，判断计数器是否大于缓冲值
 *
 * 
 * @author wubo
 *
 */
public class IDGenerator implements IdentifierGenerator{

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

	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
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
