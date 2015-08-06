package com.journaldev.spring.form.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.journaldev.spring.form.dao.EmployeeDao;
import com.journaldev.spring.form.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {

	@Autowired 
	private EmployeeDao employeeDao;

	
	public List<Employee> getAllEmployees() {		
		return employeeDao.getAllEmployees();		
	}
    public void addEmployee(Employee employee){
		employeeDao.addEmployee(employee);
		
    }
	public List<Employee> getEmployeeLogin(String user){
		return employeeDao.getEmployeeLogin(user);
	}
    
	public void updateEmployee(Employee update){
		employeeDao.updateEmployee(update);
	}
    
	/*public EmployeeService(){
		Employees = new LinkedList<Employee>();
	}
	

    
    public void deleteEmployee(int posicion){
    	Employees.remove(posicion);
    	index--;
    }
   
	
	public List<Employee> getAll(){
		return Employees;
	}
	public int size(){
		return index;
	}
	
	public Employee getEmployee(int index){
		return Employees.get(index);
	}*/
		

	
}
