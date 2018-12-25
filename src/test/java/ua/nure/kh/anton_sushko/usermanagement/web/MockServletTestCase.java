package ua.nure.kh.anton_sushko.usermanagement.web;

import java.util.Properties;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;

import ua.nure.kh.anton_sushko.usermanagement.db.DaoFactory;
import ua.nure.kh.anton_sushko.usermanagement.db.MockDaoFactory;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {
	
	protected static final String ID_ATTRIBUTE = "id";
    protected static final String TEST_FIRST_NAME = "John";
    protected static final String TEST_LAST_NAME = "Doe";
    protected static final String FIRST_NAME_ATTRIBUTE = "firstName";
    protected static final String LAST_NAME_ATTRIBUTE = "lastName";
    protected static final String DATE_ATTRIBUTE = "date";
    protected static final String OK_BUTTON = "okButton";
    private static final String DAO_FACTORY_PROPERTY = "dao.factory";

    private Mock mockUserDao;

    protected Mock getMockUserDao() {
        
        return mockUserDao;
    }

    protected void setMockUserDao(Mock mockUserDao) {
        
        this.mockUserDao = mockUserDao;
    }

    @Override
    protected void setUp() throws Exception {
        
        super.setUp();
        
        Properties properties = new Properties();
        
        properties.setProperty(DAO_FACTORY_PROPERTY, MockDaoFactory.class.getName());
        
        DaoFactory.init(properties);
        
        setMockUserDao(((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao());
    }

    @Override
    protected void tearDown() throws Exception {
        
        getMockUserDao().verify();
        
        super.tearDown();
    }
    
}