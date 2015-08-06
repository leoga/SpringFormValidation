package com.journaldev.spring.form.model;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table( name = "employee")
public class Employee implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "name")
	@NotEmpty
    private String name;
	
	@Column(name = "role")
	@NotEmpty
    private String role;
	
	@Column(name = "user")
	@NotEmpty
	private String user;
	
	@Column(name = "password")
	private String password;
	
	
	public Employee()
	{	
	}
	
	public Employee( int id, String name, String role, String user, String password){
		this.id=id;
		this.name=name;
		this.role=role;
		this.user=user;
		this.password=password;
	}
	
     
	public int getId(){
		return id;
	}
	
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getUser() {
		return user;
	}
    
    public void setUser(String user) {
		this.user = user;
	}
    
    public String getPassword() {
		return password;
	}
    
    public void setPassword(String password) {
		this.password = password;
	}
     
}