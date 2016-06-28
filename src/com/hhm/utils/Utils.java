package com.hhm.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hhm.hibernate_01_test.Student;

/**
 * 工具类
 * @author 黄帅哥
 *
 */
public class Utils {
	
	/**
	 * 获取sessionFactory工厂对象
	 * @return
	 */
	public static SessionFactory getSessionFactory(Class clazz){
		 //准备对象
		 SessionFactory sFactory=null;
		 Configuration configuration=null;
		
		
			//初始化对象
			configuration=new Configuration();
			//读取配置文件
			configuration.configure("hibernate.cfg.xml");
			//加载类的配置信息
			configuration.addClass(clazz);
			
			//获取工厂对象
			sFactory=configuration.buildSessionFactory();
			
			return sFactory;
	}
}
