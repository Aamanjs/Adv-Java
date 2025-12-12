package com.cdac;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;



public class StudentDAO {
	
	public void writeStudent(Student s) {
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		
		Session session = factory.openSession();
		
		Transaction tx = session.beginTransaction();
		
		session.persist(s);
		
		tx.commit();
		
	}
	
	public void updateStudent(Student s) {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.merge(s);
		
		tx.commit();
		session.close();
	}
	
	public void deleteStudent(int id) {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Student s = session.find(Student.class, id);
		if(s != null) {
			session.remove(s);
		}
		
		tx.commit();
		session.close();
	}
	
	public List<Student> getAllStudents(){
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory();
		
		Session session = factory.openSession();
		
		Transaction tx = session.beginTransaction();
		
		String q = "from students";
		List<Student> list = session.createQuery("from Student", Student.class).list();
		
		session.close();
		return list;
	}

}
