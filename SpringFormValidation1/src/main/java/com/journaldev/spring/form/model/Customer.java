package com.journaldev.spring.form.model;
 
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import com.journaldev.spring.form.validator.Phone;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table ( name= "customer")
public class Customer implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	//@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "idemployee")
	private int idemployee;	
	
	@Column (name = "name")
    @Size(min=2, max=30) 
    private String name;
    
	
	@Column( name = "email")
    @NotEmpty @Email
    private String email;
      
	@Column (name = "age")
    @NotNull @Min(18) @Max(100)
    private Integer age;
      
	@Column(name = "gender")
    @NotNull
    private Gender gender;
    
	@Column(name = "birthday")
    //@DateTimeFormat(pattern="dd/MM/yyyy")
    @NotNull 
    @Past
    private Date birthday;
     
	@Column( name = "phone")
    @Phone
    private String phone;
	
	@Column(name="user")
	@NotEmpty
	private String user;
	
	@Column(name = "password")
	private String password;
	
	private Timestamp currentdate;
	
	public Customer()
    {
    }

    public Customer( String name,
                     String email,
                     Integer age,
                     Gender gender,
                     Date birthday,
                     String phone)
    {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
    }
	   
    public enum Gender {
        MALE, FEMALE
    }
 
	public void setPassword(String password) {
		this.password = password;
		
	}public String getPassword() {
		return password;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
    
    public int getId(){
    	return id;
    }
    public void setId(int id){
    	this.id=id;
    }
    
    
    public String getName() {
        return name;
    }
    

    
    public void setName(String name) {
        this.name = name;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public Integer getAge() {
        return age;
    }
 
    public void setAge(Integer age) {
        this.age = age;
    }
 
    public Gender getGender() {
        return gender;
    }
 
    public void setGender(Gender gender) {
        this.gender = gender;
    }
 
    public Date getBirthday() {
        return birthday;
    }
 
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
 
    public String getPhone() {
        return phone;
    }
 
    public void setPhone(String phone) {
        this.phone = phone;
    }
     
    public int getIdemployee() {
		return idemployee;
	}
    
    public void setIdemployee(int idemployee) {
		this.idemployee = idemployee;
	}
    
    public Timestamp getCurrentdate() {
		return currentdate;
	}
    
    public void setCurrentdate(Timestamp currentdate) {
		this.currentdate = currentdate;
	}
}