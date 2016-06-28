package com.hhm.hibernate_03_guanLian;

import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * 这是测试类，测试一对多和多对一的关联方式的增，删，改，查各种功能
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
		configuration.configure("hibernate.cfg.xml");
		// 加载类映射文件
		configuration.addClass(Department.class);
		configuration.addClass(Employee.class);
		// 获取工厂对象
		sFactory = configuration.buildSessionFactory();
	}

	/**
	 * 测试添加的方法
	 */

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
	 * 测试删除的方法，
	 * 因为在employee表中，有关于department表的外键约束，所以，普通情况下无法
	 * 直接 删除department表中的对象，外键约束的默认属性值是restrict，意思是不允许删除外键，将其改为cascade后，
	 * 则可以直接删除department对象，同时也会将有关这个department对象的employee对象删除
	 */

	public static void delete() {
		Session session = sFactory.openSession();
		Transaction ts = null;

		// 删id为1的部门
		Department department = new Department();
		department.setDid(1);

		try {
			// 开始事务提交器
			ts = session.beginTransaction();

			// 删除操作
			session.delete(department);

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
	 * 测试更新的方法，
	 * 将eid为5的员工所属的部门改为3,
	 * 在获取带员工对象和部门对象后仍需双向关联，在调用update方法更新department对象
	 */

	public static void update() {
		Session session = sFactory.openSession();
		Transaction ts = null;


		try {
			// 开始事务提交器
			ts = session.beginTransaction();

			//获取eid为5的员工
			Employee employee=(Employee) session.get(Employee.class, 5);
			
			//获取did为3的部门，
			Department department=(Department) session.get(Department.class, 3);

			//双向关联
			department.getEmployees().add(employee);
			employee.setDepartment(department);
			//更新数据
			session.update(department);

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
	 * 测试查询的方法
	 */
	public static void query() {
		Session session = sFactory.openSession();
		Transaction ts = null;


		try {
			// 开始事务提交器
			ts = session.beginTransaction();

			
			
			//获取did为3的部门，
			Department department=(Department) session.get(Department.class, 3);

			System.out.println(department);
			
			//获取该部门下的所有员工，并打印出来
			Set<Employee> set=department.getEmployees();
			
			for (Employee employee : set) {
				System.out.println(employee);
			}

			// 提交数据
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
	
	public static void main(String[] args) {
		//delete();
		//update();
		query();
	}
}
