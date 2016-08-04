package lee;

import org.crazyit.app.domain.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class NewsManager {
	
	public static void main(String[] args)
		throws Exception {
		NewsManager nm = new NewsManager();
//		nm.query();
		nm.save();
	}
	
	private void query()throws Exception {
		//ʵ����Configuration��
		Configuration conf = new Configuration()
		//���淽��Ĭ�ϼ���hibernate.cfg.xml�ļ�
			.configure();
		//��Configuration����SessionFactory
		SessionFactory sf = conf.buildSessionFactory();
		//����Session
		Session sess = sf.openSession();
		//��ʼ����
		News n = (News) sess.get(News.class, new Long(2147483647));
		System.out.println(n.getId() +", " + n.getTitle() + ", " +n.getContent());
		//������Ϣʵ��
		
		//�ر�Session
		sess.close();
		sf.close();
	}
	
	private void save()throws Exception {
		//ʵ����Configuration��
		Configuration conf = new Configuration()
		//���淽��Ĭ�ϼ���hibernate.cfg.xml�ļ�
			.configure();
		//��Configuration����SessionFactory
		SessionFactory sf = conf.buildSessionFactory();
		//����Session
		Session sess = sf.openSession();
		//��ʼ����
		Transaction tx = sess.beginTransaction();
		//������Ϣʵ��
		News n = new News();
		//������Ϣ�������Ϣ����
		n.setTitle("mycat");
		n.setContent("i am zhengnengliang");
		//������Ϣ
		sess.save(n);
		//�ύ����
		tx.commit();
		//�ر�Session
		sess.close();
		sf.close();
	}
}
