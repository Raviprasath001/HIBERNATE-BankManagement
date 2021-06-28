package com.ravi.hibernate.demo.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateStudentDemo {

	
		public static void main(String[] args) {
			SessionFactory factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Students.class).buildSessionFactory();
			Session session=factory.getCurrentSession();
			try {
				System.out.println("creating the student...");
				
				Students tempStudent=new Students("paul","wall","ravi@gmail.com");

			session.beginTransaction();
			System.out.println("Saving the student...");
			session.save(tempStudent);

			session.getTransaction().commit();
			
			System.out.println("");
			}finally {
				factory.close();
			}
				}
	}


