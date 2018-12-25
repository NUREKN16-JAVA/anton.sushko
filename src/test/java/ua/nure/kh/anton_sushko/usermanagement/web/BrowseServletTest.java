package ua.nure.kh.anton_sushko.usermanagement.web;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ua.nure.kh.anton_sushko.usermanagement.User;
import ua.nure.kh.anton_sushko.usermanagement.db.DatabaseException;

public class BrowseServletTest extends MockServletTestCase {
	
    private static final String FIND_ALL_METHOD = "findAll";
    private static final String USERS_ATTRIBUTE = "users";
    private static final long TEST_ID = 1000L;
    private static final String TEST_ID_STRING = "1000";
    private static final String EDIT_BUTTON = "editButton";
    private static final String DETAILS_BUTTON = "detailsButton";
    private static final String DELETE_BUTTON = "deleteButton";

    @Override
    protected void setUp() throws Exception {
        
        super.setUp();
        
        createServlet(BrowseServlet.class);
    }

    public void testBrowse() {
        
        User expectedUser = new User(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, new Date());
        List<User> expectedUsers = Collections.singletonList(expectedUser);
        getMockUserDao().expectAndReturn(FIND_ALL_METHOD, expectedUsers);
        
        doGet();
        
        Collection<User> actualUsers = (Collection<User>) getWebMockObjectFactory().getMockSession().getAttribute(USERS_ATTRIBUTE);
        assertNotNull("Could not find list of users in session", actualUsers);
        assertEquals(expectedUsers, actualUsers);
    }

    public void testEdit() {
        
        User expectedUser = new User(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, new Date());
        getMockUserDao().expectAndReturn("find", TEST_ID, expectedUser);
        addRequestParameter(EDIT_BUTTON, "Edit");
        addRequestParameter(ID_ATTRIBUTE, TEST_ID_STRING);
        
        doPost();
        
        User actualUser = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull("Could not find user in session", actualUser);
        assertEquals(expectedUser, actualUser);
    }

    public void testDetails() {
        
        User expectedUser = new User(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, new Date());
        getMockUserDao().expectAndReturn("find", TEST_ID, expectedUser);
        addRequestParameter(DETAILS_BUTTON, "Details");
        addRequestParameter(ID_ATTRIBUTE, TEST_ID_STRING);
        
        doPost();
        
        User actualUser = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull("Could not find user in session", actualUser);
        assertEquals(expectedUser, actualUser);
    }

    public void testDelete() {
        
        User expectedUser = new User(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, new Date());
        getMockUserDao().expectAndReturn("find", TEST_ID, expectedUser);
        getMockUserDao().expect("delete", expectedUser);
        addRequestParameter(DELETE_BUTTON, "Delete");
        addRequestParameter(ID_ATTRIBUTE, TEST_ID_STRING);
        
        doPost();
    }

    public void testDeleteWithIncorrectId() {
        
        String expectedErrorMessage = "Incorrect id";
        getMockUserDao().expectAndThrow("find", TEST_ID, new DatabaseException(expectedErrorMessage));
        addRequestParameter(DELETE_BUTTON, "Delete");
        addRequestParameter(ID_ATTRIBUTE, TEST_ID_STRING);
        
        doPost();
        
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message", errorMessage);
        assertTrue(errorMessage.contains(errorMessage));
    }
}