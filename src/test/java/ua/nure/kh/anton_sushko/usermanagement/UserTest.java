package ua.nure.kh.anton_sushko.usermanagement;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class UserTest{

    private static final Long ID = 1L;
    private static final String FIRSTNAME = "Anton";
    private static final String LASTNAME  = "Sushko";
    private static Date BIRTHDATE;
    private static int BIRTHYEAR;
    private User user;

    @Before
    public void setUp() throws Exception {
    	BIRTHYEAR = 1999;
    	BIRTHDATE = new SimpleDateFormat("dd-MM-yyyy").parse("19-03-1999");
        user = new User(ID,FIRSTNAME, LASTNAME, BIRTHDATE);
    }

    @Test
    public void testAgeBirthdayToday() {
    	
    	int expected_age = 19;

        Calendar calendar = Calendar.getInstance();
        
        calendar.set(BIRTHYEAR, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        user.setBirthdate(calendar.getTime());

        int real_age = user.getAge();

        assertEquals(expected_age, real_age);
    }

    @Test
    public void testAgeBirthdayTomorrow() {
    	
    	int expected_age = 18;

        Calendar calendar = Calendar.getInstance();
        
        calendar.set(BIRTHYEAR, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        user.setBirthdate(calendar.getTime());

        int real_age = user.getAge();
        
        assertEquals(expected_age, real_age);
    }

    @Test
    public void testAgeBirthdayInOneMonth() {
    	
    	int expected_age = 18;

        Calendar calendar = Calendar.getInstance();
        
        calendar.set(BIRTHYEAR,calendar.get(Calendar.MONTH),26);
        calendar.add(Calendar.MONTH, 1);

        user.setBirthdate(calendar.getTime());

        int real_age = user.getAge();
        
        assertEquals(expected_age, real_age);
    }

    @Test
    public void testNewbornAge() {
    	
        int expected_age = 0;

        Calendar calendar = Calendar.getInstance();
        
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        user.setBirthdate(calendar.getTime());

        int real_age = user.getAge();
        
        assertEquals(expected_age, real_age);
    }


    @Test
    public void testFullName() {
    	
    	String result = new StringBuilder(FIRSTNAME).append(", ").append(LASTNAME).toString();
    	
        assertEquals(result,user.getFullName());
    }
}