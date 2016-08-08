package lee;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
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

	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
		KeySingleton key = KeySingleton.getInstance();
		return key.sequence(arg0, arg1);

	}

}
