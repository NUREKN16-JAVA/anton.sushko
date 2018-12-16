package ua.nure.kh.anton_sushko.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ua.nure.kh.anton_sushko.usermanagement.db.DatabaseException;
import ua.nure.kh.anton_sushko.usermanagement.util.Messages;

public class BrowsePanel extends JPanel implements ActionListener {

	private MainFrame parent;
	private JPanel buttonPanel;
	private JButton detailsButton;
	private JButton addButton;
	private JButton deleteButton;
	private JButton editButton;
	private JScrollPane tablePanel;
	private JTable userTable;

	public BrowsePanel(MainFrame frame) {
	parent= frame;
	initialize();
	}

	private void initialize() {
		this.setName("browsePanel");
		this.setLayout(new BorderLayout());
		this.add(getTablePanel(),BorderLayout.CENTER);
		this.add(getButtonsPanel(), BorderLayout.SOUTH);
		
	}

	private JPanel getButtonsPanel() {
		if(buttonPanel==null) {
			buttonPanel= new JPanel();
			buttonPanel.add(getAddButton(),BorderLayout.CENTER);
			buttonPanel.add(getEditButton(),BorderLayout.SOUTH);
			buttonPanel.add(getDeleteButton(),BorderLayout.EAST);
			buttonPanel.add(getDetailsButton(),BorderLayout.CENTER);
		}
		
		return buttonPanel;
	}

	

	private JButton getDetailsButton() {
		if(detailsButton==null) {
			detailsButton= new JButton();
			detailsButton.setText(Messages.getString("BrowsePanel.details"));
			detailsButton.setName("detailsButton");
			detailsButton.setActionCommand("detalis");
			detailsButton.addActionListener(this);
		
			
		}
		return detailsButton;
	}

	private JButton getDeleteButton() {
		if(deleteButton==null) {
			deleteButton= new JButton();
			deleteButton.setText(Messages.getString("BrowsePanel.delete"));
			deleteButton.setName("deleteButton");
			deleteButton.setActionCommand("delete");
			deleteButton.addActionListener(this);
		
			
		}
		return deleteButton;
		
	}

	private JButton getEditButton() {
		if(editButton==null) {
			editButton= new JButton();
			editButton.setText(Messages.getString("BrowsePanel.edit"));
			editButton.setName("editButton");
			editButton.setActionCommand("edit");
			editButton.addActionListener(this);
			
		}
		return editButton;
	}

	private JButton getAddButton() {
		if(addButton==null) {
			addButton= new JButton();
			addButton.setText(Messages.getString("BrowsePanel.add"));
			addButton.setName("addButton");
			addButton.setActionCommand("add");
			addButton.addActionListener(this);
			
		}
		return addButton;
	}

	private JScrollPane getTablePanel() {
		if(tablePanel==null) {
			tablePanel= new JScrollPane(getUserTable());
		}
		return tablePanel;
	}

	private JTable getUserTable() {
		if(userTable==null) {
			userTable= new JTable();
			userTable.setName("userTable");

		}
		return userTable;
	}

	public void initTable() {
		UserTableModel model;
		try {
			model = new UserTableModel(parent.getDao().findAll());
		} catch (DatabaseException e) {
		model = new UserTableModel(new ArrayList<>());
		JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
				JOptionPane.ERROR_MESSAGE);
		}
		userTable.setModel(model);
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if ("add".equalsIgnoreCase(actionCommand)) {
        	this.setVisible(false);
        	parent.showAddPanel();
        }
        else if("edit".equalsIgnoreCase(actionCommand)) {
        	Long id=(long) userTable.getValueAt(userTable.getSelectedRow(), 0);
        	this.setVisible(false);
        	try {
				parent.showEditPanel(parent.getDao().find(id));
			} catch (DatabaseException e1) {
				e1.printStackTrace();
			}
        }
        else if("delete".equalsIgnoreCase(actionCommand)) {
        	Long id=(long) userTable.getValueAt(userTable.getSelectedRow(), 0);
        	this.setVisible(false);
        	try {
				parent.showDeletePanel(parent.getDao().find(id));
			} catch (DatabaseException e1) {
				e1.printStackTrace();
			}
        }
        else if("details".equalsIgnoreCase(actionCommand)) {
        	Long id=(long) userTable.getValueAt(userTable.getSelectedRow(), 0);
        	this.setVisible(false);
        	try {
				parent.showDetailsPanel(parent.getDao().find(id));
			} catch (DatabaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        initTable();
        return;
        	
        }
}