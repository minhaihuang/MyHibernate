package minhaihuang.hibernate.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 标准的hibernate模板
 * @author 黄帅哥
 *
 */
public class HibernateModel {
	//准备对象
	private static SessionFactory  sessionFactory=null;
	private static Configuration configuration=null;
	
	static{
		configuration=new Configuration(); 
		//加载配置文件
		configuration.configure("hibernate.cfg.xml");
		configuration.addClass(Person.class);
		
		//获取工厂对象
		sessionFactory=configuration.buildSessionFactory();
	}
	
	/**
	 * 保存操作，插入数据到数据库，返回值是id的最大值
	 * @param obj
	 * @return
	 */
	public static int save(Object obj){
		int i=0;
		//事务转换器
		Transaction ts=null;
		//session对象
		Session session=null;
		
		try{
			//初始化对象
			session=sessionFactory.openSession();
			
			ts=session.beginTransaction();
			
			//保存数据，准备提交数据
			i=(Integer) session.save(obj);
			
			//正式提交数据
			ts.commit();
		}catch(Exception e){
			if(ts!=null){
			//出现异常，回滚操作
			ts.rollback();
			}
			//抛出新的异常
			throw new RuntimeException("服务器异常"+e);
		}finally{
			session.close();
		}
		return i;
	}
	
	public static void main(String[] args) {
		//准备数据
		Person p=new Person();
		p.setAge(21);
		p.setGender("female");
		p.setName("hhm01");
		
		System.out.println(save(p));
	}
}
