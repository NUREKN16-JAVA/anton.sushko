package ua.nure.kh.anton_sushko.usermanagement.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ua.nure.kh.anton_sushko.usermanagement.User;

public class EditServletTest extends MockServletTestCase {

    private static final String DATE_PATTERN = "dd.mm.yyyy";
    
    private static final DateFormat FORMATER = new SimpleDateFormat(DATE_PATTERN);

    @Override
    protected void setUp() throws Exception {
        
        super.setUp();
        
        createServlet(EditServlet.class);
    }

    public void testEdit() {
        
        Date date = new Date();
        User user = new User(1000L, TEST_FIRST_NAME, TEST_LAST_NAME, date);
        getMockUserDao().expect("update", user);

        addRequestParameter(ID_ATTRIBUTE, "1000");
        addRequestParameter(FIRST_NAME_ATTRIBUTE, TEST_FIRST_NAME);
        addRequestParameter(LAST_NAME_ATTRIBUTE, TEST_LAST_NAME);
        addRequestParameter(DATE_ATTRIBUTE, FORMATER.format(date));
        addRequestParameter(OK_BUTTON, "Ok");
        
        doPost();
    }

    public void testEditWithEmptyFirstName() {
        
        Date date = new Date();

        addRequestParameter(ID_ATTRIBUTE, "1000");
        addRequestParameter(LAST_NAME_ATTRIBUTE, TEST_LAST_NAME);
        addRequestParameter(DATE_ATTRIBUTE, DateFormat.getDateInstance().format(date));
        addRequestParameter(OK_BUTTON, "Ok");
        
        doPost();
        
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message", errorMessage);
    }

    public void testEditWithEmptyLastName() {
        
        Date date = new Date();

        addRequestParameter(ID_ATTRIBUTE, "1000");
        addRequestParameter(FIRST_NAME_ATTRIBUTE, TEST_FIRST_NAME);
        addRequestParameter(DATE_ATTRIBUTE, DateFormat.getDateInstance().format(date));
        addRequestParameter(OK_BUTTON, "Ok");
        
        doPost();
        
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message", errorMessage);
    }

    public void testEditWithEmptyDate() {
        
        addRequestParameter(ID_ATTRIBUTE, "1000");
        addRequestParameter(FIRST_NAME_ATTRIBUTE, TEST_FIRST_NAME);
        addRequestParameter(LAST_NAME_ATTRIBUTE, TEST_LAST_NAME);
        addRequestParameter(OK_BUTTON, "Ok");
        
        doPost();
        
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message", errorMessage);
    }

    public void testEditWithIncorrectDate() {
        
        addRequestParameter(ID_ATTRIBUTE, "1000");
        addRequestParameter(FIRST_NAME_ATTRIBUTE, TEST_FIRST_NAME);
        addRequestParameter(LAST_NAME_ATTRIBUTE, TEST_LAST_NAME);
        addRequestParameter(OK_BUTTON, "Ok");
        addRequestParameter(DATE_ATTRIBUTE, "AAAAAAAAA");

        doPost();
        
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message", errorMessage);
    }
}