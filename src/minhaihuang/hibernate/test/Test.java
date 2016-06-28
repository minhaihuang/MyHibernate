package minhaihuang.hibernate.test;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 利用hibernate对Person进行增删改查操作
 * @author 黄帅哥
 *
 */
public class Test {
	//SessionFactory创建session工厂对象
	private static SessionFactory sessionFactory=null;
	
	static{
		//准备读取配置文件的对象
		Configuration configuration=new Configuration();
		//读取配置文件
		configuration.configure("hibernate.cfg.xml");
		configuration.addClass(Person.class);
		
		//获取工厂对象
		sessionFactory=configuration.buildSessionFactory();
		
	}
	
	public static void main(String[] args) {
		//准备数据
		Person p=new Person();
		p.setId(6);
		p.setAge(18);
		p.setGender("male");
		p.setName("hzy");
		
		//addPerson(p);
		//delete(p);
		//update(p);
//		Person p2=get(Person.class,6);
//		System.out.println(p2.getName());
	}
	
	/**
	 * 增加操作
	 * @param p
	 * @return
	 */
	public static int addPerson(Person p){
		int i=0;
		//打开session对象
		Session session=sessionFactory.openSession();
		//打开事务转换器
		Transaction ts=null;
		
		try{
			ts=session.beginTransaction();
			//保存数据
			i=(Integer) session.save(p);
			//提交数据到数据库
			ts.commit();
		}catch(Exception e){
			//回滚操作
			if(ts!=null){
				ts.rollback();
				throw new RuntimeException("服务器错误"+e);
			}
		}finally{
			//关闭会话
			session.close();
		}
		
		return i;
	}
	
	/**
	 * 删除操作
	 * @param p
	 */
	public static void delete(Person p){
		//打开session对象
		Session session=sessionFactory.openSession();
		//打开事务转换器
		Transaction ts=null;
		
		try{
			ts=session.beginTransaction();
			//保存数据
			session.delete(p);
			//提交数据到数据库
			ts.commit();
		}catch(Exception e){
			//回滚操作
			if(ts!=null){
				ts.rollback();
				throw new RuntimeException("服务器错误"+e);
			}
		}finally{
			//关闭会话
			session.close();
		}
	}
	
	/**
	 * 更新操作
	 * @param p
	 */
	public static void update(Person p){
		//打开session对象
				Session session=sessionFactory.openSession();
				//打开事务转换器
				Transaction ts=null;
				
				try{
					ts=session.beginTransaction();
					//保存数据
					session.update(p);
					//提交数据到数据库
					ts.commit();
				}catch(Exception e){
					//回滚操作
					if(ts!=null){
						ts.rollback();
						throw new RuntimeException("服务器错误"+e);
					}
				}finally{
					//关闭会话
					session.close();
				}
	}
	
	/**
	 * 通过get方法查询
	 * @param clazz
	 * @param id
	 * @return
	 */
	public static Person get(Class clazz,int id){
		Person p=null;
		//打开session对象
		Session session=sessionFactory.openSession();
		//打开事务转换器
		Transaction ts=null;
		
			ts=session.beginTransaction();
			//保存数据
			p=(Person) session.get(clazz, id);
			//提交数据到数据库
			ts.commit();
	
			//关闭会话
			session.close();
		
		return p;
	}
	
	/**
	 * 通过load方法查询
	 * @param clazz
	 * @param id
	 * @return
	 */
	public static Person load(Class clazz,int id){
		Person p=null;
		//打开session对象
		Session session=sessionFactory.openSession();
		//打开事务转换器
		Transaction ts=null;
	
		ts=session.beginTransaction();
		//保存数据
		p=(Person) session.load(clazz, id);
		//提交数据到数据库
		ts.commit();
		session.close();
		return p;
	}
}
