package ua.nure.kh.anton_sushko.usermanagement.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ua.nure.kh.anton_sushko.usermanagement.User;
import ua.nure.kh.anton_sushko.usermanagement.util.Messages;

public class UserTableModel extends AbstractTableModel {
 
private static final long serialVersionUID = 1L;
private static final String[] COLUMN_NAMES = {Messages.getString("UserTableModel.id"), Messages.getString("UserTableModel.first_name"), Messages.getString("UserTableModel.last_name")}; //$NON-NLS-2$ //$NON-NLS-3$
private static final Class<?>[] COLUMN_CLASSES = {Long.class,String.class,String.class};
private List<User> users= null;
  
  public UserTableModel(Collection<User> users) {
	  this.users= new ArrayList<User>(users);
  }
	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}
    
	@Override
	public String getColumnName(int column) {
	
		return COLUMN_NAMES[column];
	
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
		return COLUMN_CLASSES[columnIndex];
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User  user = (User) users.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return user.getId();
		case 1:
			return user.getFirstname();
		case 2:
			return user.getLastname();
		}
		return null;
	}

}