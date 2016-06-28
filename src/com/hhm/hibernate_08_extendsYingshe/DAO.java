package com.hhm.hibernate_08_extendsYingshe;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 这是DAO类，复制数据的持久化操作
 * 
 * 测试继承映射关系，采用join-subclass，若有相同的属性，保存到父类表中，特殊的属性保存到子类自己的表中
 * 
 * @author 黄帅哥
 * 
 */
public class DAO {
	private static Configuration configuration = null;
	private static SessionFactory sFactory = null;

	static {
		configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addClass(Person.class);

		sFactory = configuration.buildSessionFactory();

	}

	/**
	 * 增加的操作
	 */
	public static void add() {
		Session session = sFactory.openSession();
		Transaction ts = null;

		// 准备数据
		Person p = new Person();
		p.setName("hhm");

		Student student = new Student();
		student.setName("hhc");
		student.setGrade("2");

		Teacher teacher = new Teacher();
		teacher.setName("lkf");
		teacher.setTitle("prefersser");

		try {
			// 保存数据，其中会插入三条person语句，因为name是相同的属性，所以保存到父类表中。其余数据分别只插一条
			ts = session.beginTransaction();
			session.save(p);
			session.save(teacher);
			session.save(student);

			ts.commit();
		} catch (HibernateException e) {
			if (ts != null) {
				ts.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	// 缺点：如果层次结构过深，查询速度缓慢
	public static void queryTeacher() {
		Session session = sFactory.openSession();

		Query query = session.createQuery(" from Teacher ");
		List<Teacher> teacherList = query.list();
		for (Teacher teacher : teacherList) {
			System.out.println("name:" + teacher.getName() + " title:"
					+ teacher.getTitle());
		}

		session.close();

	}

	// 演示多态 父类表分别于每个子类表做left outer join
	public static void queryPerson() {
		Session session = sFactory.openSession();

		Query query = session.createQuery(" from Person ");
		List<Person> personList = query.list();
		for (Person person : personList) {
			System.out.print("name:" + person.getName());
			if (person instanceof Teacher) {
				System.out.print(" title:" + ((Teacher) person).getTitle());
			}
			if (person instanceof Student) {
				System.out.print(" grade:" + ((Student) person).getGrade());
			}
			System.out.println();
		}

		session.close();

	}

	public static void deleteTeacher() throws Exception {
		Session session = sFactory.openSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Teacher teacher = new Teacher();
			teacher.setId(2);
			session.delete(teacher);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}

	}

	/**
	 * 注意此处hql语句的写法
	 */
	public static void queryPersonNotTeacherOrStudent() {
		Session session = sFactory.openSession();

		StringBuffer hql = new StringBuffer();
		hql.append(" from  Person p ");
		//不包含该id
		hql.append(" where    p.id not in (select t.id from Teacher t ) ");
		hql.append("     and  p.id not in (select s.id from Student s ) ");

		Query query = session.createQuery(hql.toString());
		List<Person> personList = query.list();
		for (Person person : personList) {
			System.out.print("name:" + person.getName());

			System.out.println();
		}

		session.close();

	}

	public static void main(String[] args) {
		//add();
		//queryPersonNotTeacherOrStudent();
		//queryPerson();
		//queryTeacher();
		
	}
}
