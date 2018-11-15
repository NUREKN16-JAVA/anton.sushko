package ua.nure.kn.anton_sushko.usermanagement;


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
    
    public Long get_id() {
    	
        return id;
    }

    public void set_id(Long id) {
    	
        this.id = id;
    }

    public String get_firstname() {
    	
        return firstname;
    }

    public void set_firstname(String firstname) {
    	
        this.firstname = firstname;
    }

    public String get_lastname() {
    	
        return lastname;
    }

    public void set_lastname(String lastname) {
    	
        this.lastname = lastname;
    }

    public Date get_birthdate() {
    	
        return birthdate;
    }

    public void set_birthdate(Date birthdate) {
    	
        this.birthdate = birthdate;
    }

    
    /*	public String getFullName()
     * 	requires: нет ограничений
     * 	effects: полное имя пользоватея в формате firstname + " ," + lastname.
     */    
    public String getFullName() {
    	
        return new StringBuilder(get_firstname()).append(", ").append(get_lastname()).toString();
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