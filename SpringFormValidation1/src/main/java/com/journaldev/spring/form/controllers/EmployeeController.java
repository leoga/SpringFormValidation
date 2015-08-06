package com.journaldev.spring.form.controllers;
 

import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.journaldev.spring.form.login.Login;
import com.journaldev.spring.form.model.Employee;
import com.journaldev.spring.form.model.Customer;
import com.journaldev.spring.form.service.CustomerService;
import com.journaldev.spring.form.service.EmployeeService;
import com.journaldev.spring.form.search.SearchFields;
import com.journaldev.spring.form.search.SearchById;
 
@Controller
public class EmployeeController {
 
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    
    @Autowired
    private EmployeeService Employees;
    @Autowired
    private CustomerService Customers;
    private Employee currentEmployee;
    private Customer modifyCustomer;
    int pagelist, pagesearchlist;
    Timestamp[] Dates;
    SearchFields sfields;
    boolean getpost = false;
    List<Employee> employeeLog = new LinkedList();
 
    @RequestMapping(value = "/emp/personalPage")
    public String getPersonalPage(Model model){
    	model.addAttribute("employeeDB", currentEmployee);
    	return "employeePage";
    }
    
    @RequestMapping(value = "/modify")
    public String modifyCustomerAction(@ModelAttribute("ArrayID") SearchById searchById, Model model){
    	Customer customerDB= new Customer();
    	logger.info("Returning modifyCustomer.jsp");
    	String selectedCustomer = searchById.getCustomer();
    	List<Customer> customers = Customers.getCustomersbyIDcustomer(Integer.parseInt(selectedCustomer));
    	if(customers.size()!=0){	
    		customerDB = customers.get(0);
    		modifyCustomer=customerDB;
    		System.out.println("Customer with ID "+Integer.parseInt(selectedCustomer)+" selected");
    	}else{
    		System.out.println("ERROR. Impossible search of customer's ID \n");
    	}
    	model.addAttribute("customerGET", customerDB);
    	return "modifyCustomer";
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String addInfoPost(@ModelAttribute("customerGET") @Valid Customer customer, BindingResult bindingresult, Model model) {
   	 if(bindingresult.hasErrors()){
   		 return "modifyCustomer";
   }
   	modifyCustomer.setUser(customer.getUser());
   	modifyCustomer.setName(customer.getName());
   	modifyCustomer.setAge(customer.getAge());
   	modifyCustomer.setBirthday(customer.getBirthday());
  	modifyCustomer.setEmail(customer.getEmail());
    modifyCustomer.setPhone(customer.getPhone());
   	Customers.updateCustomer(modifyCustomer);
    //model.addAttribute("customerDB", currentCustomer);
  	return "redirect:mycustomers?page="+pagelist;
  }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String DeletePost(@ModelAttribute("ArrayID") SearchById searchById, Model model) {
    	String[] selectedCustomers = searchById.getCustomers();
	   	for(int j=0;j<selectedCustomers.length;j++) {
	   	List<Customer> customers = Customers.getCustomersbyIDcustomer(Integer.parseInt(selectedCustomers[j]));
	   	System.out.println("Customer with ID "+Integer.parseInt(selectedCustomers[j])+" selected");
	   	if(customers.size()!=0) {
	   			Customers.deleteCustomer(customers.get(0));
	   	}
	   	else
	   		System.out.println("ERROR. Impossible search of customer's ID \n");
	   	}
	   	return "redirect:mycustomers?page="+pagelist;
    }
    
    @RequestMapping(value = "/emp/edit")
    public String editEmployeeAction(Model model){
    	logger.info("Returning customerPage-edit.jsp");
    	model.addAttribute("employeeGET", currentEmployee);
    	System.out.println("Edit GET");
    	return "employeePage-edit";
    }
    
    @RequestMapping(value = "/emp/edit", method = RequestMethod.POST)
    public String addInfoPost(@ModelAttribute("employeeGET") @Valid Employee employee, BindingResult bindingresult, Model model) {
   	 if(bindingresult.hasErrors()){
   		System.out.println("Edit POST errors");
   		 return "employeePage-edit";
   }
   	System.out.println("Edit POST");
   	currentEmployee.setUser(employee.getUser());
   	currentEmployee.setName(employee.getName());
   	currentEmployee.setRole(employee.getRole());
   	Employees.updateEmployee(currentEmployee);
   	model.addAttribute("employeeDB", currentEmployee);
  	return "employeePage";
  }
    
    @RequestMapping(value = {"/emp/loginEmp", "/loginEmp"}, method = RequestMethod.GET)
    public String logPage(Model model) {
        logger.info("Returning login.jsp page");
        model.addAttribute("employeeLogin", new Login());
        return "loginEmp";
    }
    
    @RequestMapping(value = "/loginEmp.do", method = RequestMethod.POST)
    public String getEmployeePost(@ModelAttribute("employeeLogin") Login login, Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException{
   	//List<Customer> EmployeeLog = Customers.getAllCustomer();
   	employeeLog = Employees.getEmployeeLogin(login.getUser());
   	System.out.println("login: "+login.getUser());
   	if(employeeLog.size()==1){
   		currentEmployee=employeeLog.get(0);
   	}else{
   		//System.out.println("customerLog Size: "+employeeLog.size()+" Usuario: "+employeeLog.get(0).getUser());
   		return "loginEmp";
   	}
   	String savedfailpass = login.getPassword();
   	login.setPassword(login.ReturnedHash(login.getPassword()));
   	if(login.getPassword().equals(currentEmployee.getPassword())){
   		//List<Customer> customerDB = Customers.getAllCustomer();
   		model.addAttribute("employeeDB", currentEmployee);  
   		logger.info("Returning employeePage.jsp page");
   		return "employeePage";
   	} else {
   		login.setPassword(savedfailpass);
   		return "loginEmp";
   	}
 }   
 
    @ModelAttribute("employee")
    public Employee createEmployeeModel() {
        // ModelAttribute value should be same as used in the empSave.jsp
        return new Employee();
    }
 
    @RequestMapping(value = "add-customer")
    public String addCustomerGet(Model model) {
    model.addAttribute("customer", new Customer());
    return "add-customer";
    }
    
    
    @RequestMapping(value = "add-customer", method = RequestMethod.POST)
    public String addCustomerPost(Model model, 
    		@Valid Customer customer, BindingResult bindingresult) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    Login log = new Login();
    if(bindingresult.hasErrors()){
       		 return "add-customer";
       }    	
        logger.info("Returning employeePage.jsp page");
        customer.setPassword(log.ReturnedHash("0000"));
        customer.setIdemployee(currentEmployee.getId());
        customer.setCurrentdate(CurrentDate());
        Customers.createCustomer(customer);
        model.addAttribute("employeeDB", currentEmployee);
        return "employeePage";
    }
 
    @RequestMapping(value = "/emp/save", method = RequestMethod.GET)
    public String saveEmployeePage(Model model) {
        logger.info("Returning empSave.jsp page");
        return "empSave";
    }
    
    @RequestMapping(value = "/emp/save.do", method = RequestMethod.POST)
    public String saveEmployeeAction(
    		@ModelAttribute("employee") @Validated Employee employee,
            BindingResult bindingResult, Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	Login log = new Login();
        if (bindingResult.hasErrors()) {
            logger.info("Returning empSave.jsp page");
            return "empSave";
        }else if(employeeLog.size()==0 || employeeLog.get(0).getUser().equals(currentEmployee.getUser())){
	    	logger.info("Returning loginEmp.jsp page");
	    	model.addAttribute("employeeLogin", new Login());
	        String password = employee.getPassword();
	        employee.setPassword(log.ReturnedHash(password));             
	        Employees.addEmployee(employee);
	    	return "redirect:loginEmp";
        }  
        Employees.addEmployee(employee);
        List<Employee> employees = Employees.getAllEmployees();
        model.addAttribute("employees", employees);
        logger.info("Returning empSaveSuccess.jsp page");
        /*model.addAttribute("emp", employee);
        emps.put(employee.getIndex(), employee);*/
        return "empSaveSuccess";
    }
    
    @RequestMapping(value = "mycustomers", method = RequestMethod.GET)
    public String showMyCustomers(@RequestParam(value="page") int start, Model model){
    	logger.info("Returning mycustomers.jsp page");
    	int pages;
    	int maxElements = 2;
    	List<Customer> customersById = Customers.getCustomersbyID(currentEmployee.getId());
    	model.addAttribute("ArrayID", new SearchById());
    	model.addAttribute("currentemployee", currentEmployee);
    	int n = customersById.size();
    	int offset = (start-1)*maxElements;
    	if(n%maxElements == 0){
    		pages= n/maxElements;
    	}else{
    		pages=(n/maxElements)+1;
    	}
    	List<Customer> customerDB = Customers.getCustomersbyLimit(currentEmployee.getId(), offset, maxElements);
    	model.addAttribute("customerDB", customerDB);
    	model.addAttribute("start", start);
    	model.addAttribute("pages", pages); 
    	
    	pagelist= start;
    	return "mycustomers";
    }
    
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String getSearch(@RequestParam(value="page") int start, Model model) {
    	if(getpost==true){
    	   	model.addAttribute("currentemployee", currentEmployee);
    	   	model.addAttribute("Searchfields", sfields);
    	   	model.addAttribute("ArrayID", new SearchById());
    	    if(sfields!=null){
    	      	 if(sfields.getByname()!=null && sfields.getByagehigh()==0 && sfields.getByagelow()==0 && sfields.getBydatehigh()==null && sfields.getBydatelow()==null){
    	      		 List<Customer> searching = Customers.getCustomersbyName(currentEmployee.getId(), sfields.getByname());
    	      		 PaginationSearch(model, searching, start, 2, 1);
    	      	 }
    	      	 else if(sfields.getByagehigh()!=0 && sfields.getByagelow()!=0 && sfields.getByname()==null && sfields.getBydatehigh()==null && sfields.getBydatelow()==null) {
    	      		 List<Customer> searching = Customers.getCustomersbyAge(currentEmployee.getId(), sfields.getByagehigh(), sfields.getByagelow());
    	      		 PaginationSearch(model, searching, start, 2, 2);
    	      	 }
    	      	 else if(sfields.getBydatehigh()!=null && sfields.getBydatelow()!=null && sfields.getByname()==null && sfields.getByagehigh()==0 && sfields.getByagelow()==0){
    	      		Dates= TimestampConverter(sfields.getBydatehigh(), sfields.getBydatelow());
    	      		Timestamp timehigh = Dates[0];
    	      		Timestamp timelow = Dates[1];
    	      		model.addAttribute("timehigh", timehigh);
    	      		model.addAttribute("timelow", timelow);
    	      		List<Customer> searching = Customers.getCustomersbyDate(currentEmployee.getId(), timehigh, timelow);
    	      		PaginationSearch(model, searching, start, 2, 3);
    	      	 }
    	      	 else {
    	      		 List<Customer> searching = Customers.getCustomersbyNameAge(currentEmployee.getId(), sfields.getByname(), sfields.getByagehigh(), sfields.getByagelow());
    	      		 PaginationSearch(model, searching, start, 2, 4);
    	      	 }
    	       }
    	       pagesearchlist= start;
    	       return "search";
    	}
	   	model.addAttribute("currentemployee", currentEmployee);
	   	model.addAttribute("Searchfields", new SearchFields());
	   	model.addAttribute("ArrayID", new SearchById());
	   	System.out.println("Search GET");
	   	return "search";
    }
    
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String doSearch(@RequestParam(value="page") int start, @ModelAttribute("Searchfields") SearchFields modDel, Model model, @ModelAttribute("ArrayID") SearchById searchbyid) {
    System.out.println("Search POST");	
    model.addAttribute("currentemployee", currentEmployee);
    if(modDel!=null){
    	System.out.println("modDel no es Null");
    	sfields=modDel;
    }

    if(sfields!=null){
   	 if(sfields.getByname()!=null && sfields.getByagehigh()==0 && sfields.getByagelow()==0 && sfields.getBydatehigh()==null && sfields.getBydatelow()==null){
   		 List<Customer> searching = Customers.getCustomersbyName(currentEmployee.getId(), sfields.getByname());
   		 PaginationSearch(model, searching, start, 2, 1);
   	 }
   	 else if(sfields.getByagehigh()!=0 && sfields.getByagelow()!=0 && sfields.getByname()==null && sfields.getBydatehigh()==null && sfields.getBydatelow()==null) {
   		 List<Customer> searching = Customers.getCustomersbyAge(currentEmployee.getId(), sfields.getByagehigh(), sfields.getByagelow());
   		 PaginationSearch(model, searching, start, 2, 2);
   	 }
   	 else if(sfields.getBydatehigh()!=null && sfields.getBydatelow()!=null && sfields.getByname()==null && sfields.getByagehigh()==0 && sfields.getByagelow()==0){
   		Dates= TimestampConverter(sfields.getBydatehigh(), sfields.getBydatelow());
   		Timestamp timehigh = Dates[0];
   		Timestamp timelow = Dates[1];
   		model.addAttribute("timehigh", timehigh);
   		model.addAttribute("timelow", timelow);
   		List<Customer> searching = Customers.getCustomersbyDate(currentEmployee.getId(), timehigh, timelow);
   		PaginationSearch(model, searching, start, 2, 3);
   	 }
   	 else {
   		 List<Customer> searching = Customers.getCustomersbyNameAge(currentEmployee.getId(), sfields.getByname(), sfields.getByagehigh(), sfields.getByagelow());
   		 PaginationSearch(model, searching, start, 2, 4);
   	 }
    }
    sfields = modDel;
    pagesearchlist= start;
    getpost=true;
    return "search";
    }
    
    @RequestMapping(value = "/modifys")
    public String modifyCustomerSearchAction(@ModelAttribute("ArrayID") SearchById searchById, Model model){
    	Customer customerDB= new Customer();
    	logger.info("Returning modifyCustomer.jsp");
    	String selectedCustomer = searchById.getCustomer();
    	List<Customer> customers = Customers.getCustomersbyIDcustomer(Integer.parseInt(selectedCustomer));
    	if(customers.size()!=0){	
    		customerDB = customers.get(0);
    		modifyCustomer=customerDB;
    		System.out.println("Customer with ID "+Integer.parseInt(selectedCustomer)+" selected");
    	}else{
    		System.out.println("ERROR. Impossible search of customer's ID \n");
    	}
    	
    	model.addAttribute("customerGET", customerDB);
    	return "modifyCustomer";
    }
    
    @RequestMapping(value = "/modifys", method = RequestMethod.POST)
    public String ModifySearchPost(@ModelAttribute("customerGET") @Valid Customer customer, 
    								BindingResult bindingresult, Model model) {
   	 if(bindingresult.hasErrors()){
   		 return "modifyCustomer";
   }
   	model.addAttribute("currentemployee", currentEmployee);
   	modifyCustomer.setUser(customer.getUser());
   	modifyCustomer.setName(customer.getName());
   	modifyCustomer.setAge(customer.getAge());
   	modifyCustomer.setBirthday(customer.getBirthday());
  	modifyCustomer.setEmail(customer.getEmail());
    modifyCustomer.setPhone(customer.getPhone());
   	Customers.updateCustomer(modifyCustomer);
  	return "redirect:search?page="+pagesearchlist;
  }
    
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    public String DeleteSearchPost(@ModelAttribute("ArrayID") SearchById searchById, Model model) {
    	String[] selectedCustomers = searchById.getCustomers();
	   	for(int j=0;j<selectedCustomers.length;j++) {
	   	List<Customer> customers = Customers.getCustomersbyIDcustomer(Integer.parseInt(selectedCustomers[j]));
	   	System.out.println("Customer with ID "+Integer.parseInt(selectedCustomers[j])+" selected");
	   	if(customers.size()!=0) {
	   			Customers.deleteCustomer(customers.get(0));
	   	}
	   	else
	   		System.out.println("ERROR. Impossible search of customer's ID \n");
	   	}
	   	return "redirect:search?page="+pagesearchlist;
    }
    

    
    //NOT CONTROLLERS
    
    public Timestamp[] TimestampConverter(Date timehigh, Date timelow) { 
   	 Calendar high = Calendar.getInstance();
   	 Calendar low = Calendar.getInstance();
   	 
   	 high.setTime(timehigh);
   	 low.setTime(timelow);
   	 high.set(Calendar.MILLISECOND,0);
   	 low.set(Calendar.MILLISECOND,0);
   	 Timestamp[] Dates = new Timestamp[2];
   	 Dates[0]= new Timestamp (high.getTimeInMillis()+86399999);
   	 Dates[1]= new Timestamp (low.getTimeInMillis());
   	 return Dates;
    }
    
    public Timestamp CurrentDate() {
   	 Date today = new Date();
   	 return new Timestamp(today.getTime());
    }

    
    public void PaginationSearch(Model model, List<Customer> searching, int start, int numregist, int type){
   	 int n = searching.size();
   	 int pages;
   	 if(n%numregist == 0){
   		 pages= n/numregist;
   	 }else{
   		 pages=(n/numregist)+1;
   	 }
   	 int ncustomers;
   	 if(searching.size()==0)
   		 ncustomers=0;
   	 else
   		 ncustomers = searching.size();
   	 model.addAttribute("start", start);
   	 model.addAttribute("pages", pages);
   	 model.addAttribute("ncustomers",ncustomers);
   	 if(searching.size()!=0){
   		 switch(type){
   		 case 1:  List<Customer> listing = Customers.getCustomersbyNameLimit(currentEmployee.getId(), sfields.getByname(), numregist*(start-1), numregist);
   		 		  model.addAttribute("listing",listing); break;
   		 case 2: List<Customer> listing2 = Customers.getCustomersbyAgeLimit(currentEmployee.getId(), sfields.getByagehigh(), sfields.getByagelow(), numregist*(start-1), numregist);
   		 		 model.addAttribute("listing",listing2); break;
   		 case 3: Timestamp timehigh = Dates[0];
   		 		 Timestamp timelow = Dates[1];
   			 	 List<Customer> listing3 = Customers.getCustomersbyDateLimit(currentEmployee.getId(),timehigh, timelow, numregist*(start-1), numregist);
    		 		 model.addAttribute("listing",listing3); break;
   		 case 4: List<Customer> listing4 = Customers.getCustomersbyNameAgeLimit(currentEmployee.getId(), sfields.getByname(), sfields.getByagehigh(), sfields.getByagelow(), numregist*(start-1), numregist);
   		 		 model.addAttribute("listing",listing4); break;	 
   		 }
   	  }
    }
    
    @InitBinder
    public void binder(WebDataBinder binder) {
   	 binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
   		    public void setAsText(String value) {
   		            try {
   						setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
   					} catch (ParseException e) {
   						e.printStackTrace();
   						setValue(null);
   					}
   		    
   		    }

   		    public String getAsText() {
   		    	if (null!=getValue())
   		    		return new SimpleDateFormat("dd/MM/yyyy").format((Date) getValue());
   		    	else
   		    		return "";
   		    }        

   		});
    }
    
}

