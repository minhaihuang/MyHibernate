package com.hhm.hibernate_04_guanLian_many2many;

import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 在这里测试多对多的关联模式下的增删改查操作
 * @author 黄帅哥
 *
 */
public class DAO {
	private static Configuration configuration=null;
	private static SessionFactory sFactory=null;
	
	static{
		configuration=new Configuration();
		//读取配置文件
		configuration.configure("hibernate.cfg.xml");
		//读取类映射文件
		configuration.addClass(Course.class);
		configuration.addClass(Student.class);
		
		//获取工厂对象
		sFactory=configuration.buildSessionFactory();
	}
	
	/**
	 * 增加的方法
	 */
	public static void add(){
		Session session=sFactory.openSession();
		Transaction ts=null;
		
		Student s1=new Student("hhm");
		Student s2=new Student("hhc");
		
		Course c1=new Course("math");
		Course c2=new Course("chinese");
		
		//双向关联，一个学生选两门课
		c1.getStudents().add(s1);
		c1.getStudents().add(s2);
		c2.getStudents().add(s1);
		c2.getStudents().add(s2);
		
		s1.getCourses().add(c1);
		s1.getCourses().add(c2);
		s2.getCourses().add(c1);
		s2.getCourses().add(c2);
		
		
		try {
			ts=session.beginTransaction();
			
			session.save(c2);
			session.save(c1);
			session.save(s2);
			session.save(s1);
			
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	
	/**
	 * 删除的方法，删除sid为2的学生，其所选的课程将会与其解除关系，但是不会删除课程
	 */
	public static void deleteStudent(){
		Session session=sFactory.openSession();
		Transaction ts=null;
		
		Student student=new Student();
		student.setSid(2);
		
		
		try {
			ts=session.beginTransaction();
			
			session.delete(student);
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	/**
	 * 删除的方法，删除cid为2的课程，会与选了这么课程的学生解除关系，但是不会删除学生
	 */
	public static void deleteCourse(){
		Session session=sFactory.openSession();
		Transaction ts=null;
		
		Course course=new Course();
		course.setCid(2);
		
		
		try {
			ts=session.beginTransaction();
			
			session.delete(course);
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	/**
	 * 更新的方法，将sid为3的学生所选的课程所选的课程3改为课程6
	 * 
	 * 注意：要先解除关系，在新增关系
	 */
	public static void update(){
		Session session=sFactory.openSession();
		Transaction ts=null;
		
		Student student=(Student) session.load(Student.class, 3);
		Course course=(Course) session.get(Course.class, 3);
		
		//双方解除关系
		student.getCourses().remove(course);
		course.getStudents().remove(student);
		
		//新增关系
		Course course6=(Course) session.get(Course.class, 6);
		
		student.getCourses().add(course6);
		course6.getStudents().add(student);
		
		
		try {
			ts=session.beginTransaction();
			
			//保存修改
			session.update(course6);
			session.update(course);
			session.update(student);
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	/**
	 * 查询所有的选了课程6的学生
	 */
	public static void query(){
		Session session=sFactory.openSession();
		Transaction ts=session.beginTransaction();
		
		//准备数据
		Course course6=(Course) session.get(Course.class, 6);
		
		Set<Student> students=course6.getStudents();
		
		for (Student student : students) {
			System.out.println(student.getSname());
		}
		
		ts.commit();
		session.close();
	}
	public static void main(String[] args) {
		//add();
		//deleteStudent();
		//deleteCourse();
		//update();
		query();
	}
}
