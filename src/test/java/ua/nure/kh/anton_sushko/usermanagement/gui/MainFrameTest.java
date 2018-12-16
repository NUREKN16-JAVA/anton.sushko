package ua.nure.kh.anton_sushko.usermanagement.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kh.anton_sushko.usermanagement.User;
import ua.nure.kh.anton_sushko.usermanagement.db.DaoFactory;
import ua.nure.kh.anton_sushko.usermanagement.db.DaoFactoryImpl;
import ua.nure.kh.anton_sushko.usermanagement.db.MockDaoFactory;
import ua.nure.kh.anton_sushko.usermanagement.db.MockUserDao;
import ua.nure.kh.anton_sushko.usermanagement.util.Messages;

public class MainFrameTest extends JFCTestCase {

	private MainFrame mainFrame;
	private Mock mockUserDao;
	protected void setUp() throws Exception {
		super.setUp();
		
		Properties properties= new Properties();
		properties.setProperty("dao.nure.kn.zapichnyi.usermanagement.db.UserDao",
				MockUserDao.class.getName());
			DaoFactory.getInstance().init(properties);
		mockUserDao= ((MockDaoFactory)DaoFactory.getInstance()).getMockUserDao();
		mockUserDao.expectAndReturn("findAll", new ArrayList());
		setHelper(new JFCTestHelper());
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);	
		}

	protected void tearDown() throws Exception {
		try {
			//mockUserDao.verify();
			
			if(mainFrame == null) {

				mainFrame = new MainFrame();
			}

			mainFrame.setVisible(false);			
			getHelper();
			TestHelper.cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	 
	private Component find(Class componentClass,String name) {
		
		NamedComponentFinder finder; 
		finder= new NamedComponentFinder(componentClass,name);
		finder.setWait(0);
		Component component = finder.find(mainFrame,0);
		assertNotNull("Could not find component '"+name+"'",component);
		return component;
		
	}
	public void testBrowseControls() {
		find(JPanel.class, "browsePanel");
		
		JTable table = (JTable) find(JTable.class, "userTable");
		
		assertEquals(3, table.getColumnCount());
		assertEquals(Messages.getString("UserTableModel.id"), table.getColumnName(0));
		assertEquals(Messages.getString("UserTableModel.first_name"), table.getColumnName(1));
		assertEquals(Messages.getString("UserTableModel.last_name"), table.getColumnName(2));

		
		find(JButton.class,"addButton");
		find(JButton.class,"editButton");
		find(JButton.class,"deleteButton");
		find(JButton.class,"detailsButton");
	}
	
	@SuppressWarnings("deprecation")
	public void testAddUser() {
		
		String firstName = "John";
		String last_name = "Doe";
		Date now = new Date();
		User user = new User(firstName,last_name,now);
		User expectedUser= new User(new Long(1),firstName,last_name,now);
		mockUserDao.expectAndReturn("create", user,expectedUser);
		ArrayList<User> users= new ArrayList<User>();
		users.add(expectedUser);
		mockUserDao.expectAndReturn("findAll", users);
		
		JTable table = (JTable) find(JTable.class, "userTable"); 
		assertEquals(0, table.getRowCount());
		JButton addButton= (JButton) find(JButton.class,"addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this,addButton));
		
		find(JPanel.class,"addPanel");
		
		JTextField firstNameField = (JTextField) find(JTextField.class,"firstNameField");
		JTextField lastNameField =(JTextField) find(JTextField.class,"lastNameField");
	    JTextField dateOfBirthField =(JTextField) find(JTextField.class,"birthdateField");
		JButton okButton=(JButton) find(JButton.class,"okButton");
		JButton cancelButton = (JButton) find(JButton.class,"cancelButton");
		
		
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		
		getHelper().sendString(new StringEventData(this, lastNameField, last_name));
		DateFormat formatter =  DateFormat.getInstance();
		
		String date= formatter.format(now);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		
		getHelper().enterClickAndLeave(new MouseEventData(this,okButton));
		
		find(JPanel.class,"browsePanel");
	    table = (JTable) find(JTable.class, "userTable");
		
	}	

}