package com.hhm.hibernate_03_guanLianTest;

import java.util.Date;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Oracle10gDialect;

/**
 * 能实现以下功能：
1.添加订单
2.删除订单
3.修改订单明细所属订单号
4.根据订单号查询订单

 * @author 黄帅哥
 *
 */
public class DAO {
	private static Configuration configuration=null;
	private static SessionFactory sFactory=null;
	
	static{
		configuration=new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addClass(Order.class);
		configuration.addClass(OrderItem.class);
		
		sFactory=configuration.buildSessionFactory();
	}
	
	/**
	 * 增加订单，同时增加订单项
	 */
	public static void addOrder(){
		Session session=sFactory.openSession();
		Transaction ts=session.beginTransaction();
		
		//准备数据
		
		//新增一张订单
		Order order=new Order();
		order.setOrderTime(new Date());
		order.setOrderSN("hahaha");
		order.setOrderPerson("hhm");
		
		//新增几个订单项
		OrderItem orderItem1=new OrderItem("apple", 6000, "great");
		OrderItem orderItem2=new OrderItem("meizu", 900, "soso");
		OrderItem orderItem3=new OrderItem("sum", 6000, "good");
		
		//双向关联
		order.getOrderItems().add(orderItem1);
		order.getOrderItems().add(orderItem2);
		order.getOrderItems().add(orderItem3);
		
		orderItem1.setOrder(order);
		orderItem2.setOrder(order);
		orderItem3.setOrder(order);
		
		try {
			//保存数据操作
			session.save(order);
			
			//提交数据
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();//回滚操作
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	/**
	 * 删除订单，同时删除相关的订单项。
	 * 要在数据库的orderItem表中改变外键约束删除时的值才能删除成功，
	 * 默认是restrict，要改为cascade
	 */
	public static void deleteOrder(){
		Session session=sFactory.openSession();
		Transaction ts=session.beginTransaction();
		
		//准备数据
		
		//删除orderId为3的订单
		Order order=new Order();
		order.setOrderId(3);
		
		try {
			//保存数据操作
			session.delete(order);
			
			//提交数据
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();//回滚操作
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	
	/**
	 * 更新订单项，把orderItemId为2的订单项的orderId改为2
	 */
	public static void updateOrder(){
		Session session=sFactory.openSession();
		Transaction ts=session.beginTransaction();
		
		//获取数据
		//获取订单项
		OrderItem orderItem=(OrderItem) session.get(OrderItem.class, 2);
		//获取orderId为2的订单
		Order order=(Order) session.get(Order.class, 2);
		
		//双向关联
		order.getOrderItems().add(orderItem);
		orderItem.setOrder(order);
		
		try {
			//保存数据操作
			session.update(order);
			
			//提交数据
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();//回滚操作
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	/**
	 * 查询orderId为2的订单，同时查询它的订单项
	 */
	public static void queryOrder(){
		Session session=sFactory.openSession();
		Transaction ts=session.beginTransaction();
		
		
		
		try {
			//查询订单
			Order order=(Order) session.get(Order.class, 2);
			System.out.println(order.getOrderPerson());
			
			Set<OrderItem> orderItems=order.getOrderItems();
			
			for (OrderItem orderItem : orderItems) {
				System.out.println(orderItem.getQuantity());
			}
			
			//提交数据
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();//回滚操作
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	
	public static void main(String[] args) {
		//addOrder();
		//deleteOrder();
		//updateOrder();
		queryOrder();
	}
}
