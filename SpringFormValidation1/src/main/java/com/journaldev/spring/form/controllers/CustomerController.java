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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.journaldev.spring.form.service.CustomerService;
import com.journaldev.spring.form.login.Login;
import com.journaldev.spring.form.model.Customer;



@Controller
public class CustomerController {
 
	
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    
    @Autowired
    private CustomerService Customers;
    private Customer currentCustomer;
    private List<Customer> customerLog = new LinkedList();
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model) {
        logger.info("Returning home.jsp page");
        return "home";
    }
  
    @RequestMapping(value = {"/cust/loginCust", "/loginCust"}, method = RequestMethod.GET)
    public String logPage(Model model) {
        logger.info("Returning login.jsp page");
        model.addAttribute("customerLogin", new Login());
        return "loginCust";
    }
    
    @RequestMapping(value = {"/loginCust.do","/cust/loginCust.do"}, method = RequestMethod.POST)
    public String getCustomerPost(@ModelAttribute("customerLogin") Login login, Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException{
   	//List<Customer> EmployeeLog = Customers.getAllCustomer();
   	customerLog = Customers.getCustomerLogin(login.getUser());
   	System.out.println("login: "+login.getUser());
   	if(customerLog.size()==1){
   		currentCustomer=customerLog.get(0);
   	}else{
   		return "loginCust";
   	}
   	String savedfailpass = login.getPassword();
   	login.setPassword(login.ReturnedHash(login.getPassword()));
   	if(login.getPassword().equals(currentCustomer.getPassword())){
   		//List<Customer> customerDB = Customers.getAllCustomer();
   		model.addAttribute("customerDB", currentCustomer);  
   		return "customerPage";
   	} else {
   		login.setPassword(savedfailpass);
   		return "loginCust";
   	}
 }
    @RequestMapping(value = "/cust/personalPage")
    public String getPersonalPage(Model model){
    	model.addAttribute("customerDB", currentCustomer);
    	return "customerPage";
    }
      
    @RequestMapping(value = "/cust/edit")
    public String editCustomerAction(Model model){
    	logger.info("Returning customerPage-edit.jsp");
    	model.addAttribute("customerGET", currentCustomer);
    	return "customerPage-edit";
    }
    
    @RequestMapping(value = "/cust/edit", method = RequestMethod.POST)
    public String addInfoPost(@ModelAttribute("customerGET") @Valid Customer customer, BindingResult bindingresult, Model model) {
   	 if(bindingresult.hasErrors()){
   		 return "customerPage-edit";
   }
   	currentCustomer.setUser(customer.getUser());
   	currentCustomer.setName(customer.getName());
   	currentCustomer.setAge(customer.getAge());
   	currentCustomer.setBirthday(customer.getBirthday());
  	currentCustomer.setEmail(customer.getEmail());
    currentCustomer.setPhone(customer.getPhone());
   	Customers.updateCustomer(currentCustomer);
    model.addAttribute("customerDB", currentCustomer);
  	return "customerPage";
  }
    
    @RequestMapping(value = "/cust/save", method = RequestMethod.GET)
    public String saveCustomerPage(Model model) {
        logger.info("Returning custSave.jsp page");
        model.addAttribute("customer", new Customer());
        return "custSave";
    }
 
	@RequestMapping(value = "/cust/save.do", method = RequestMethod.POST)
    public String saveCustomerAction(
            @Valid Customer customer,
            BindingResult bindingResult, Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	Login log = new Login();
        if (bindingResult.hasErrors()) {logger.info("Returning custSave.jsp page");
            return "custSave";
        }else if(customerLog.size()==0 || customerLog.get(0).getUser().equals(currentCustomer.getUser())){
        	customer.setCurrentdate(CurrentDate());
        	logger.info("Returning loginCust.jsp page");
        	model.addAttribute("customerLogin", new Login());
            String password = customer.getPassword();
            customer.setPassword(log.ReturnedHash(password));            
            Customers.addCustomer(customer);
        	return "redirect:loginCust";
        }        
        System.out.println("Customerlog: "+customerLog.get(0).getUser());
        System.out.println("Customerlog: "+currentCustomer.getUser());
        String password = customer.getPassword();
        customer.setPassword(log.ReturnedHash(password));
        logger.info("Returning custSaveSuccess.jsp page");             
        Customers.addCustomer(customer);
        List<Customer> customerDB = Customers.getAllCustomer();
        model.addAttribute("customerDB", customerDB);  
        //customers.put(customer.getEmail(), customer);
        return "custSaveSuccess";
    }
 
//NOT CONTROLLERS

@InitBinder
public void binderC(WebDataBinder binder) {
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

public Timestamp CurrentDate() {
 	 Date today = new Date();
 	 return new Timestamp(today.getTime());
  }

}



