package com.journaldev.spring.form.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.journaldev.spring.form.model.Employee;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class EmployeeDao {
	 
	@Autowired
	private SessionFactory sessionFactory;
 
	@SuppressWarnings("unchecked")
	public List<Employee> getAllEmployees() {
		Session session = sessionFactory.getCurrentSession();		
		Query q = session.createQuery("from Employee e order by id desc");
		//Query q = session.createQuery("select NAME from Customer");
		List<Employee> employeeList = q.list(); 
	    return employeeList;			
	}

	public void addEmployee(Employee employee) {
		Session session = sessionFactory.getCurrentSession();	
		session.save(employee);
	}
	
	 @SuppressWarnings("unchecked")
	 public List<Employee> getEmployeeLogin(String user) {
	 return sessionFactory.getCurrentSession()
	 		 .createQuery("FROM Employee e WHERE user=:user" )
	 		 .setString("user",user)
	 		 .list();
	 }
	 public void updateEmployee(Employee update) {
		 sessionFactory.getCurrentSession().saveOrUpdate(update);
	 }
	 
}
