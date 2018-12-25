package ua.nure.kh.anton_sushko.usermanagement.web;

import ua.nure.kh.anton_sushko.usermanagement.User;

public class DetailsServletTest extends MockServletTestCase {

    private static final String BACK_BUTTON = "backButton";
    
    private static final String USER_ATTRIBUTE = "user";

    @Override
    protected void setUp() throws Exception {
        
        super.setUp();
        
        createServlet(DetailsServlet.class);
    }

    public void testBack() {
        
        addRequestParameter(BACK_BUTTON, "Back");
        
        User user = (User) getWebMockObjectFactory().getMockSession().getAttribute(USER_ATTRIBUTE);
        
        assertNull("User was not deleted from session", user);
    }
}