package com.journaldev.spring.form.service;


import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.journaldev.spring.form.model.Customer;
import com.journaldev.spring.form.dao.CustomerDao;

@Service
@Transactional
public class CustomerService {
	
	@Autowired 
	private CustomerDao customerDAO;
	
	
    public void addCustomer(Customer customer){
    		customerDAO.addCustomer(customer);
    }
	public List<Customer> getAllCustomer() {		
		return customerDAO.getAllCustomers();		
	}
	
	public List<Customer> getCustomerLogin(String user){
		return customerDAO.getCustomerLogin(user);
	}
	
	public void updateCustomer(Customer update){
		customerDAO.updateCustomer(update);
	}
	public List<Customer> getCustomersbyLimit(int IDemployee, int first, int last){
		return customerDAO.getCustomersbyLimit(IDemployee, first, last);
	}
	
	public List<Customer> getCustomersbyID(int idEmployee){
		return customerDAO.getCustomersbyID(idEmployee);
	}
	
	public List<Customer> getCustomersbyIDcustomer(int IDcustomer) {
		return customerDAO.getCustomersbyIDcustomer(IDcustomer);
	}
	
	public List<Customer> getCustomersbyName(int IDemployee, String byname) {
		return customerDAO.getCustomersbyName(IDemployee, byname);
	}
	
	public List<Customer> getCustomersbyAge(int IDemployee, int byagehigh, int byagelow) {
		return customerDAO.getCustomersbyAge(IDemployee, byagehigh, byagelow);
	}
	
	public List<Customer> getCustomersbyDate(int IDemployee, Timestamp bydatehigh, Timestamp bydatelow) {
		return customerDAO.getCustomersbyDate(IDemployee, bydatehigh, bydatelow);
	}

	 public List<Customer> getCustomersbyNameAge(int IDemployee, String byname, int byagehigh, int byagelow) {
		 return customerDAO.getCustomersbyNameAge(IDemployee, byname, byagehigh, byagelow);
	 }
	 
	 public void deleteCustomer(Customer customer) {
		 customerDAO.deleteCustomer(customer);
	 }
	 
	 public void createCustomer(Customer customer) {
		 customerDAO.createCustomer(customer);
	 }
	 
	 public List<Customer> getCustomersbyNameLimit(int IDemployee, String byname, int first, int last) {
		 return customerDAO.getCustomersbyNameLimit(IDemployee, byname, first, last);
	 }
	 
	 public List<Customer> getCustomersbyAgeLimit(int IDemployee, int byagehigh, int byagelow, int first, int last) {
		 return customerDAO.getCustomersbyAgeLimit(IDemployee, byagehigh, byagelow, first, last);
	 }
	 
	 public List<Customer> getCustomersbyDateLimit(int IDemployee, Timestamp bydatehigh, Timestamp bydatelow, int first, int last) {
		 return customerDAO.getCustomersbyDateLimit(IDemployee, bydatehigh, bydatelow, first, last);
	 }
	 
	 public List<Customer> getCustomersbyNameAgeLimit(int IDemployee, String byname, int byagehigh, int byagelow, int first, int last) {
		 return customerDAO.getCustomersbyNameAgeLimit(IDemployee, byname, byagehigh, byagelow, first, last);
	 }
		 
}



 

 

 


