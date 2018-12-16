
package ua.nure.kh.anton_sushko.usermanagement.db;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import ua.nure.kh.anton_sushko.usermanagement.User;

import java.util.Collection;
import java.util.Date;

public class HsqlUserDaoTest extends TestCase {

    private static final int TEST_DATA_LENGTH = 3;

    private static final Long EXISTING_ID = 2L;
    private static final String EXISTING_FIRST_NAME = "John";
    private static final String EXISTING_LAST_NAME = "Doe";
    @SuppressWarnings("deprecation")
	private static final Date EXISTING_DATE_OF_BIRTH = new Date(72, 5, 8);

    private static final Long NEW_ID = 2L;
    private static final String NEW_FIRST_NAME = "Elon";
    private static final String NEW_LAST_NAME = "Musk";
    @SuppressWarnings("deprecation")
	private static final Date NEW_DATE_OF_BIRTH = new Date(71, 6, 28);

    private HsqlUserDao dao;
    private ConnectionFactory connectionFactory;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.dao = new HsqlUserDao(connectionFactory);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl(
                "org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:db/usermanagement",
                "sa",
                "");
        return new DatabaseConnection(connectionFactory.createConnection());
    }
    
    
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("users_data_set.xml"));
        return dataSet;
    }

    @Test
    public void testCreate() throws DatabaseException {
        User user = new User();
        user.setFirstname("Anton");
        user.setLastname("Sushko");
        user.setBirthdate(new Date());
        assertNull(user.getId());

        User createdUser = this.dao.create(user);
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(user.getFirstname(), createdUser.getFirstname());
        assertEquals(user.getLastname(), createdUser.getLastname());
    }

    @Test
    public void testFind() {
        try {
            User foundUser = this.dao.find(EXISTING_ID);

            assertEquals(foundUser.getId(), EXISTING_ID);
            assertEquals(foundUser.getFirstname(), EXISTING_FIRST_NAME);
            assertEquals(foundUser.getLastname(), EXISTING_LAST_NAME);
            assertEquals(foundUser.getBirthdate(), EXISTING_DATE_OF_BIRTH);

        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void testFindAll() {
        try {
            Collection<User> result = this.dao.findAll();

            assertNotNull("Query result is null.", result);
            assertEquals("Collection size in not TEST_DATA_LENGTH", result.size(), TEST_DATA_LENGTH);

            IDataSet databaseDataSet = this.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("users");

            IDataSet expectedDataSet = this.getDataSet();
            ITable expectedTable = expectedDataSet.getTable("users");

            Assertion.assertEquals(expectedTable, actualTable);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void testUpdate() {
        try {
            User user = new User(NEW_ID, NEW_FIRST_NAME, NEW_LAST_NAME, NEW_DATE_OF_BIRTH);
            this.dao.update(user);

            IDataSet databaseDataSet = this.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("users");

            IDataSet expectedDataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSetAfterUpdate.xml"));
            ITable expectedTable = expectedDataSet.getTable("users");

            Assertion.assertEquals(expectedTable, actualTable);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void testDelete() {
        try {
            User user = new User(EXISTING_ID, EXISTING_FIRST_NAME, EXISTING_LAST_NAME, EXISTING_DATE_OF_BIRTH);
            this.dao.delete(user);

            IDataSet databaseDataSet = this.getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("users");

            IDataSet expectedDataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSetAfterDelete.xml"));
            ITable expectedTable = expectedDataSet.getTable("users");

            Assertion.assertEquals(expectedTable, actualTable);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}