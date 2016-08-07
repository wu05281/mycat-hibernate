package lee;

import java.io.Serializable;
import java.util.Date;

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
	 * 缓存长度
	 */
	private static int seqCache = 0;
	
	private static int count = 0;
	private static int sequence = 0;

	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
		Session session = arg0.getFactory().openSession();
		Transaction tx = session.beginTransaction();
		//持久化对象  
	      AbstractEntityPersister classMetadata =  (AbstractEntityPersister) arg0.getFactory().getClassMetadata(arg1.getClass());
	      String tableName = classMetadata.getTableName();//表名
	      
		if (seqCache == 0 ) {
			SQLQuery query = session.createSQLQuery("SELECT mycat_seq_nextval('"+tableName +"')");
			String seq = (String) query.uniqueResult();
			String[] seqs = seq.split(",");
			//seqSenCach = Integer.valueOf(seqs[1]);
			//seqFirCach = Integer.valueOf(seqs[0]);
		} 
//		int sequence = seqFirCach + seqSenCach;
		String id = new Date().getTime() + "" + sequence;
//		System.out.println(id);
//		seqSenCach++;
		tx.commit();
		session.close();
		return Long.valueOf(id);
		
	}

}
