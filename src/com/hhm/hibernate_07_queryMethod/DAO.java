package com.hhm.hibernate_07_queryMethod;

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
 * 测试各种不同的检索方式
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

	public static void queryMethod() {
		Session session = sFactory.openSession();

		// 检索方式一：HQL
		// Query
		// query=session.createQuery("from Employee e where e.name='hhm'");
		//
		// List<Employee> hhms=query.list();
		//
		// for (Employee employee : hhms) {
		// System.out.println(employee);
		// }

		// 检索方式二：QBC
		// Criteria criteria=session.createCriteria(Employee.class);
		// Criterion criterion=Restrictions.eq("name", "hhm");
		// criteria.add(criterion);
		// List<Employee> employees=criteria.list();
		// for (Employee employee : employees) {
		// System.out.println(employee);
		// }

		// 检索方式三：SQL，注意是session.createSQLQuery，而不是session.createQuery
		// Query
		// sqlQuery=session.createSQLQuery("select * from Employee where name='hhm'");
		// List<Object[]> employees=sqlQuery.list();
		// for (Object[] objectArray : employees) {
		// System.out.println(objectArray[0]+"  "+objectArray[1]+"  "+objectArray[2]);

		// 2.多态查询
		// 两个类一起查询
		// Query query=session.createQuery("from java.io.Serializable");
		// List<Object> objs=query.list();
		//
		// for (Object obj : objs) {
		// System.out.println(obj);
		// }

		// Query query=session.createQuery("from  java.lang.Object ");
		// List<Object> list = query.list();
		// for (Object object : list) {
		// System.out.println(object);
		// }

		// 3，结果集排序
		// Query
		// query=session.createQuery("from Employee e order by e.salary asc");
		// List<Employee> employees=query.list();
		// for (Employee employee : employees) {
		// System.out.println(employee);
		// }

		// 4，分页查询，下标是0开始
		// Query query=session.createQuery("from Employee");
		// query.setFirstResult(3);//代表的是第四条，下标是0开始
		// query.setMaxResults(5);
		// List<Employee> employees=query.list();
		// for (Employee employee : employees) {
		// System.out.println(employee);
		// }

		// 5,检索单个对象
		// Query
		// query=session.createQuery("from Employee e where e.name='hhm' order by e.salary desc ");
		// query.setMaxResults(1);//表示只取一条记录，若没有这句话，将会报错
		// Employee employee=(Employee) query.uniqueResult();
		// System.out.println(employee);

		// 6，绑定参数
		// 按照参数名绑定
		// Query
		// query=session.createQuery("from Employee e where e.name=:name and e.eid=:eid ");
		// query.setString("name", "hhm");
		// query.setString("eid", "5");
		// List<Employee> employees=query.list();
		// for (Employee employee : employees) {
		// System.out.println(employee);
		// }

		// 按照参数的位置绑定，下标从0开始
		// Query query=session.createQuery("from Employee where name=? ");
		// query.setString(0, "hhm");
		// List<Employee> employees=query.list();
		// for (Employee employee : employees) {
		// System.out.println(employee);
		// }

		// 7.使用映射文件中定义的命名查询语句，在该Employee的配置文件中定义有标签(在class标签外定义)
		// <query name="findEmployeeByDname">
		// <![CDATA[ from Employee e where e.name like ? ]]>
		// </query>
		Query query = session.getNamedQuery("findEmployeeByDname");
		query.setString(0, "hhm");
		List<Employee> list = query.list();
		for (Employee employee : list) {
			System.out.println(employee);
		}

		session.close();
	}

	/**
	 * 测试联合查询
	 */
	public static void testJoinQuery() {

		Session session = sFactory.openSession();
		// 1，联合左外连接查询
		//返回的是一个对象数组的集合，对象数组的第0个元素表示from后面操作的对象，即department对象
//		Query query = session
//				.createQuery("from Department d left outer join d.employees where d.name='market'");
//		List<Object[]> list = query.list();
//		for (Object[] objects : list) {
//			System.out.println(objects[0] + ":" + objects[1]);
//		}
		
		//2，迫切左外连接擦查询，返回的是from后面的Department对象
//		Query query=session.createQuery("from Department d left outer join fetch d.employees where d.name='market'");
//		//因为部门为market的员工有8个，所以list的size为8
//		List<Department> list=query.list();
//		for (Department department : list) {
//			System.out.println(list.size());
//			System.out.println(department);
//		}
		
		//3.内连接  inner join 返回的是一个对象数组的集合，第0个元素表示的是from后面的对象
//		Query query = session.createQuery("from Department d inner join d.employees where d.name='market' ");
//		List<Object[]> objectArrayList = query.list();
//		for(Object[] objectArray:objectArrayList){
//	//		System.out.println(objectArray.length);
//			System.out.println(objectArray[0]);
//			System.out.println(objectArray[1]);			
//		}
		
		//4.迫切内连接 返回的是对象集合，该对象为from后面的对象
//		Query query = session.createQuery("from Department d inner join  " +
//				"fetch d.employees where d.name='market' ");
//		List<Department> departmentList = query.list();
//		for(Department department:departmentList){
//			System.out.println(department);
//			for(Employee employee:department.getEmployees()){
//				System.out.println(employee);
//			}
//		}
		
		
		//5.右链接，right outer join=right join
		//返回的是一个对象数组的集合，对象数组的第0个元素表示from后面操作的对象
		Query query = session.createQuery("from Department d right join d.employees where d.name='market' ");
		List<Object[]> objectArrayList = query.list();
		System.out.println(objectArrayList.size());
		for(Object[] objectArray:objectArrayList){
	//		System.out.println(objectArray.length);
			System.out.println(objectArray[0]);
			System.out.println(objectArray[1]);			
		}
		session.close();
	}

	/**
	 * 投影查询（查找部分属性）和报表查询和分组查询
	 */
	public static void testGroudByandOthers(){
		Session session =sFactory.openSession();
		
		//1，投影查询，查询部门的名字，和部门的只能
//		Query query=session.createQuery("select d.name,d.brief from Department d");
//	
//		List<Object[]> list=query.list();
//		for (Object[] obj : list) {
//			System.out.println(obj[0]+":"+obj[1]);
//		}
		
		//2，用构造方法实现投影查询，要在bean中有该构造函数的方法才能实现此功能
//		Query query=session.createQuery("select new Employee(e.eid,e.name,e.salary) from Employee e");
//		List<Employee> employees=query.list();
//		for (Employee employee : employees) {
//			System.out.println(employee);
//		}
		
		//3，当存在多个相同的元素时，只取一个。注意distinct的用法
//		Query query = session.createQuery("select distinct e.name from Employee e where eid<? ");
//		query.setInteger(0, 3);
//		List<Object> list = query.list();
//		for(Object objectArray:list){
//			System.out.println(objectArray);
//			
//		}
		
		//4，报表查询，即各种聚合函数
		//查询有多少条数据
//		Query query=session.createQuery("select count(*) from Employee e");
//		List<Integer> list=query.list();
//		System.out.println(list.get(0));//8
		//查询最大工资的人数和最小工资的人数，若有多条数据，只取一条
//		Query query = session.createQuery("select max(salary),min(salary) from Employee e  ");
//		query.setMaxResults(1);
//		Object[] objectArray = (Object[]) query.uniqueResult();
//		System.out.println("max salary:"+objectArray[0]+" min salary: "+objectArray[1]);
		
		//5，group by 分组查询
		//获取名字相同的条数大于1的记录的名字和条数，并且根据名字来分组
		Query query=session.createQuery(" select e.name,count(*) from Employee e group by e.name having count(*)>1 ");
		List<Object[]> list = query.list();
		for(Object[] objectArray:list){
			System.out.println(objectArray[0]+":"+objectArray[1]);
			
		}
		
		session.close();
	
	}
	
	public static void main(String[] args) {
		//testJoinQuery();
		// queryMethod();
		// add();
		testGroudByandOthers();
	}
}
