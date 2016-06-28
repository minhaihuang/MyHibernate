package com.hhm.hibernate_10_secondLevalCache;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * 测试二级缓存
 * 
 * @author 黄帅哥
 * 
 */
public class DAO {
	private static Configuration configuration = null;
	private static SessionFactory sFactory = null;

	// 读取配置文件
	static {
		configuration = new Configuration();
		// 读取总配置文件；
		configuration
				.configure("com/hhm/hibernate_10_secondLevalCache/hibernate.cfg.xml");
		// 加载类映射文件
		configuration.addClass(Department.class);
		configuration.addClass(Employee.class);
		// 获取工厂对象
		sFactory = configuration.buildSessionFactory();
	}

	public static void add() {
		Session session = sFactory.openSession();
		Transaction ts = null;

		// 新开一个部门
		Department department = new Department();
		department.setName("market");// 市场部
		department.setBrief("do sales");// 职能是市场营销

		// 招进来几个员工
		Employee e1 = new Employee("hhm", 8000);
		Employee e2 = new Employee("hzy", 6000);
		Employee e3 = new Employee("hhc", 8000);
		Employee e4 = new Employee("h y", 6000);

		// 双向关联，员工关联部门。部门关联员工
		e1.setDepartment(department);
		e2.setDepartment(department);
		e3.setDepartment(department);
		e4.setDepartment(department);

		// 部门关联员工
		department.getEmployees().add(e1);
		department.getEmployees().add(e2);
		department.getEmployees().add(e3);
		department.getEmployees().add(e4);

		try {
			// 开始事务提交器
			ts = session.beginTransaction();

			// 只需保存department到session中便可同时分别往员工表和部门表中插入数据
			session.save(department);

			// 提交数据，执行sql语句
			ts.commit();
		} catch (HibernateException e) {
			if (ts != null) {
				ts.rollback();// 回滚操作
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	/**
	 * 测试二级缓存的溢出问题
	 */
	public static void testSecondCacheProblem() {
		Session session = sFactory.openSession();
		// Session session2=sFactory.openSession();
		// System.out.println(session==session2);//false，不是同一个对象

		// Session session3=sFactory.getCurrentSession();
		// Session session4=sFactory.getCurrentSession();
		// System.out.println(session3==session4);//true，当调用sFactor的getCurrentSession()
		// 方法时，如果当前进程中没有session，则会新创一个；如果已经有了，则会返回已有的session，所以两者相等

		/*
		 * 获取所有的Employee对象，因为超过10个，所以会增加溢出缓存， 多出的数据会保存到ehcache.xml文件指定的目录中去
		 */
		Query query = session.createQuery("from Employee");

		List<Employee> employees = query.list();

		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	/**
	 * 查找数据的顺序：一级缓存（session）->二级缓存——>数据库
	 * 
	 * 深入理解二级缓存：
	 */
	public static void testSecondCache() {
		Session session = sFactory.openSession();
		Transaction ts = session.beginTransaction();

		Department d = (Department) session.get(Department.class, 1);// 第一次查询，产生sql语句
		System.out.println(d);

		Department d2 = (Department) session.get(Department.class, 1);
		System.out.println(d2);// 第二次查询，session中有缓存，不会产生sql语句
		ts.commit();
		session.close();// 清空一级缓存

		session = sFactory.openSession();// 新开一级缓存
		Department d3 = (Department) session.get(Department.class, 1);
		System.out.println(d3);// 第三次查询，二级缓存中有缓存，不会产生sql语句

		System.out.println(d == d2);// true
		System.out.println(d == d3);// false，证明了二级缓存的数据跟一级缓存的数据形式不同，二级的是散装的数据

		session.close();

	}

	/**
	 * 测试同步更新：当一级缓存的数据更新时，二级缓存和数据库的数据都会同步更新
	 */
	public static void testSecondCacheUpdate() {
		Session session = sFactory.openSession();
		Transaction ts = session.beginTransaction();

		Department d = (Department) session.get(Department.class, 1);// 第一次查询，产生sql语句
		System.out.println(d);

		d.setName("hr");// 改变名字
		System.out.println(d);
		ts.commit();
		session.close();// 清空一级缓存，同步数据库

		session = sFactory.openSession();// 新开一级缓存
		Department d3 = (Department) session.get(Department.class, 1);
		System.out.println(d3);// 不产生sql语句，但是名字发生了变化，证明了同步更新

		session.close();
	}

	/**
	 * 測試时间戳，比较查询时间和更新时间的先后带来的不同
	 */
	public static void testUpdateTimeStampCache() {

		Session session = sFactory.openSession();
		Transaction tr = session.beginTransaction();
		Department department = (Department) session.get(Department.class, 1);// generate
																				// select
		System.out.println(department);

		// session的get和load方法操作的都是一级缓存，二hql语句操作的是二级缓存
		Query query = session
				.createQuery("update Department d set d.name='market' where d.id=1 ");
		query.executeUpdate();
		tr.commit();
		session.close();

		session = sFactory.openSession();
		tr = session.beginTransaction();
		department = (Department) session.get(Department.class, 1);
		System.out.println(department);
		tr.commit();
		session.close();

	}

	

	/**
	 * 测试查询缓存
	 */
	public static void testQueryCache() {

		Session session = sFactory.openSession();
		Transaction tr = session.beginTransaction();
		Query query = session.createQuery(" from Department ");//产生sql语句
		query.setCacheable(true);//每一次使用都需要设置
		List<Department> departmentList = query.list();
		for (Department d : departmentList) {
			System.out.println(d);
		}
		tr.commit();
		session.close();

		session = sFactory.openSession();
		tr = session.beginTransaction();
		//不产生sql语句。直接从查询缓存中获取
		query = session.createQuery(" from Department ");
		query.setCacheable(true);//每一次使用都需要设置
		departmentList = query.list();
		for (Department d : departmentList) {
			System.out.println(d);
		}
		tr.commit();
		session.close();

	}

	public static void main(String[] args) {

		// add();
		// testSecondCache();
		//testSecondCacheUpdate();
		//testUpdateTimeStampCache();
		testQueryCache();
	}
}
