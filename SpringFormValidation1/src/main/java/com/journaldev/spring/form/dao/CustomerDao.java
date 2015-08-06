package com.journaldev.spring.form.dao;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.journaldev.spring.form.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class CustomerDao {
	 
	@Autowired
	private SessionFactory sessionFactory;
 
	@SuppressWarnings("unchecked")
	public List<Customer> getAllCustomers() {
		Session session = sessionFactory.getCurrentSession();		
		Query q = session.createQuery("from Customer c order by id desc");
		//Query q = session.createQuery("select NAME from Customer");
		List<Customer> customerList = q.list(); 
	    return customerList;			
	}

	public void addCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();	
		session.save(customer);
	}
	
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomerLogin(String user) {
	 return sessionFactory.getCurrentSession()
	 		 .createQuery("FROM Customer c WHERE user=:user" )
	 		 .setString("user",user)
	 		 .list();
	 }
	 
	 public void updateCustomer(Customer update) {
		 sessionFactory.getCurrentSession().saveOrUpdate(update);
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomersbyLimit(int IDemployee, int first, int last) {
		 return sessionFactory.getCurrentSession()
			.createQuery("FROM Customer c WHERE idemployee=:ID")
			.setInteger("ID", IDemployee)
			.setFirstResult(first)
			.setMaxResults(last)
			.list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomersbyID(int IDemployee) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Customer c WHERE idemployee=:ID")
	  .setInteger("ID", IDemployee)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomersbyIDcustomer(int IDcustomer) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Customer c WHERE id=:ID")
	  .setInteger("ID", IDcustomer)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomersbyName(int IDemployee, String byname) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Customer c WHERE idemployee=:ID AND name LIKE :N")
	  .setInteger("ID", IDemployee)
	  .setString("N", "%"+byname+"%")
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomersbyAge(int IDemployee, int byagehigh, int byagelow) {
	// System.out.printf("ID: %s High: %s Low: %s",IDemployee, byagehigh, byagelow);
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Customer c WHERE idemployee=:id AND age BETWEEN :L AND :H")
	  .setParameter("id", IDemployee)
	  .setParameter("L", byagelow)
	  .setParameter("H", byagehigh)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomersbyDate(int IDemployee, Timestamp bydatehigh, Timestamp bydatelow) {
	// System.out.printf("ID: %s High: %s Low: %s",IDemployee, byagehigh, byagelow);
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Customer c WHERE idemployee=:id AND currentdate BETWEEN :L AND :H")
	  .setParameter("id", IDemployee)
	  .setParameter("L", bydatelow)
	  .setParameter("H", bydatehigh)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomersbyNameAge(int IDemployee, String byname, int byagehigh, int byagelow) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Customer c WHERE idemployee=:ID AND age BETWEEN :L AND :H AND name LIKE :N")
	  .setInteger("ID", IDemployee)
	  .setInteger("H", byagehigh)
	  .setInteger("L", byagelow)
	  .setString("N", "%"+byname+"%")
	  .list();
	 }
	 
	 public void deleteCustomer(Customer customer) {
		 sessionFactory.getCurrentSession().delete(customer);
	 }
	 
	 public void createCustomer(Customer customer) {
	 sessionFactory.getCurrentSession().save(customer);
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomersbyNameLimit(int IDemployee, String byname, int first, int last) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Customer c WHERE idemployee=:ID AND name LIKE :N")
	  .setInteger("ID", IDemployee)
	  .setString("N", "%"+byname+"%")
	  .setFirstResult(first)
	  .setMaxResults(last)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomersbyAgeLimit(int IDemployee, int byagehigh, int byagelow, int first, int last) {
	// System.out.printf("ID: %s High: %s Low: %s",IDemployee, byagehigh, byagelow);
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Customer c WHERE idemployee=:id AND age BETWEEN :L AND :H")
	  .setParameter("id", IDemployee)
	  .setParameter("L", byagelow)
	  .setParameter("H", byagehigh)
	  .setFirstResult(first)
	  .setMaxResults(last)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomersbyDateLimit(int IDemployee, Timestamp bydatehigh, Timestamp bydatelow, int first, int last) {
	// System.out.printf("ID: %s High: %s Low: %s",IDemployee, byagehigh, byagelow);
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Customer c WHERE idemployee=:id AND currentdate BETWEEN :L AND :H")
	  .setParameter("id", IDemployee)
	  .setParameter("L", bydatelow)
	  .setParameter("H", bydatehigh)
	  .setFirstResult(first)
	  .setMaxResults(last)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Customer> getCustomersbyNameAgeLimit(int IDemployee, String byname, int byagehigh, int byagelow, int first, int last) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Customer c WHERE idemployee=:ID AND age BETWEEN :L AND :H AND name LIKE :N")
	  .setInteger("ID", IDemployee)
	  .setInteger("H", byagehigh)
	  .setInteger("L", byagelow)
	  .setString("N", "%"+byname+"%")
	  .setFirstResult(first)
	  .setMaxResults(last)
	  .list();
	 }
	
}