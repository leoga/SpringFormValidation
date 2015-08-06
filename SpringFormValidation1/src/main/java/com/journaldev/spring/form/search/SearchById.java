package com.journaldev.spring.form.search;

public class SearchById {
	
	private String[] customers = new String[100];
	private String customer = new String();
	
	public String[] getCustomers() {
		return customers;
	}
	
	public void setCustomers(String[] customers) {
		this.customers = customers;
	}
	
	public String getCustomer() {
		return customer;
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}
}
