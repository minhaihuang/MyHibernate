package com.hhm.hibernate_11_improveSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAO {
	
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static SessionFactory sessionFactory;
	static {
		Configuration configuration = new Configuration();
		
		configuration.configure("hibernate.cfg.xml");
		configuration.addClass(Student.class);
		
		sessionFactory = configuration.buildSessionFactory();
	}

	
	
	public static Session openSession() {
		Session session = threadLocal.get();
		if (session == null || !session.isOpen()) {
			session = sessionFactory.openSession();
			threadLocal.set(session);
		}
		return session;
	}

	public static void closeSession() {
		Session session = threadLocal.get();
		threadLocal.set(null);
		if (session != null && session.isOpen()) {
			session.close();
		}
	}

}
