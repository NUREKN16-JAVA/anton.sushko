package ua.nure.kh.anton_sushko.usermanagement;


import java.io.Serializable;
import java.time.ZoneId;
import java.time.LocalDate;
import java.util.Date;
import  java.time.temporal.ChronoUnit;

public class User implements Serializable{
	
    private static final long serialVersionUID = 4188062179191390064L;
    private Long id;
    private String firstname;
    private String lastname;
    private Date birthdate;

    public User() {
    	
    }

    public User(Long id, String firstname, String lastname, Date birthdate) {
    	
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }
    
    public User(String firstname, String lastname, Date birthdate) {
    	
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }
    
    public Long getId() {
    	
        return id;
    }

    public void setId(Long id) {
    	
        this.id = id;
    }

    public String getFirstname() {
    	
        return firstname;
    }

    public void setFirstname(String firstname) {
    	
        this.firstname = firstname;
    }

    public String getLastname() {
    	
        return lastname;
    }

    public void setLastname(String lastname) {
    	
        this.lastname = lastname;
    }

    public Date getBirthdate() {
    	
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
    	
        this.birthdate = birthdate;
    }

    
    /*	public String getFullName()
     * 	requires: нет ограничений
     * 	effects: полное имя пользоватея в формате firstname + " ," + lastname.
     */    
    public String getFullName() {
    	
        return new StringBuilder(getFirstname()).append(", ").append(getLastname()).toString();
    }

    
    /*	public int geAge() {
     * 	requires: нет ограничений
     * 	effects: количество полных лет пользователя в формате целочисленного значения
     */
    public int getAge() {
    	
    	LocalDate current_date = LocalDate.now();
    	
        LocalDate LD_birthdate = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    			
        return (int) ChronoUnit.YEARS.between(LD_birthdate, current_date);
    }
}