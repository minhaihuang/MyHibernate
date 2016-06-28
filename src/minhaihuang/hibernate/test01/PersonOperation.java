package minhaihuang.hibernate.test01;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 操作person类，包括增删改查
 * @author 黄帅哥
 *
 */
public class PersonOperation {
	//准备对象
	private static Configuration configuration=null;
	private static SessionFactory sFactory=null;
	//初始化代码
	static{
		configuration=new Configuration();
		//读取配置文件
		configuration.configure("hibernate.cfg.xml");
		configuration.addClass(Person.class);
		
		//获取工厂对象
		sFactory=configuration.buildSessionFactory();
	}
	
	/**
	 * 增加一个person
	 */
	public static void addPerson(){
		//准备数据
		Person person=new Person();
	
		person.setName("hhm");
		person.setAge(21);
		person.setGender("male");
		
		
		//准备Session 对象
		Session session=null;
		//准备事务提交器对象
		Transaction ts=null;
		try {
			//打开session对象
			session=sFactory.openSession();
			//准备事务提交器
			ts=session.beginTransaction();
			//保存数据到session中
			session.save(person);
			//提交书屋
			ts.commit();
		} catch (Exception e) {
			ts.rollback();//若有异常，回滚操作
			e.printStackTrace();
		}finally{
			session.close();
		}			
	}
	
	/**
	 * 删除一个person,根据id来删除，因为id是主键
	 */
	public static void deletePerson(){
		//准备数据
		Person person=new Person();
		person.setId(1);

		//准备Session 对象
		Session session=null;
		//准备事务提交器对象
		Transaction ts=null;
		try {
			//打开session对象
			session=sFactory.openSession();
			//准备事务提交器
			ts=session.beginTransaction();
			//保存数据到session中
			session.delete(person);
			//提交书屋
			ts.commit();
		} catch (Exception e) {
			ts.rollback();//若有异常，回滚操作
			e.printStackTrace();
		}finally{
			session.close();
		}			
	}
	
	/**
	 * 更新一个person，更加id类似更新，因为id是主键
	 */
	public static void updatePerson(){
		//准备数据
		Person person=new Person();
		person.setId(2);
		person.setName("cqc");
		person.setAge(22);
		person.setGender("male");
		
		
		//准备Session 对象
		Session session=null;
		//准备事务提交器对象
		Transaction ts=null;
		try {
			//打开session对象
			session=sFactory.openSession();
			//准备事务提交器
			ts=session.beginTransaction();
			//保存数据到session中
			session.update(person);
			//提交书屋
			ts.commit();
		} catch (Exception e) {
			ts.rollback();//若有异常，回滚操作
			e.printStackTrace();
		}finally{
			session.close();
		}			
	}
	
	/**
	 * 查找一个person，用get方法，也是根据id来查找
	 */
	public static void queryPerson(){
		//准备Session 对象
		Session session=null;
		//准备事务提交器对象
		Transaction ts=null;
		try {
			//打开session对象
			session=sFactory.openSession();
			//准备事务提交器
			ts=session.beginTransaction();
			//保存数据到session中
			Person p=(Person) session.get(Person.class, 2);
			System.out.println(p.getName());
			//提交事务
			ts.commit();
		} catch (Exception e) {
			ts.rollback();//若有异常，回滚操作
			e.printStackTrace();
		}finally{
			session.close();
		}			
	}
	
	/**
	 * 查询一个person，用load方法，也是根据id来查找
	 */
	public static void queryPerson1(){
	
		//准备Session 对象
		Session session=null;
		//准备事务提交器对象
		Transaction ts=null;
		try {
			//打开session对象
			session=sFactory.openSession();
			//准备事务提交器
			ts=session.beginTransaction();
			//保存数据到session中
			Person p=(Person) session.load(Person.class, 2);
			//System.out.println(p.getName());
			//提交事务
			ts.commit();
		} catch (Exception e) {
			ts.rollback();//若有异常，回滚操作
			e.printStackTrace();
		}finally{
			session.close();
		}			
	}
	
	
	public static void main(String[] args) {
		//addPerson();
		//deletePerson();
		//updatePerson();
		queryPerson1();
	}
}
