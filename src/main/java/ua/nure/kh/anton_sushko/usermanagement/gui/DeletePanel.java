package ua.nure.kh.anton_sushko.usermanagement.gui;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ua.nure.kh.anton_sushko.usermanagement.User;
import ua.nure.kh.anton_sushko.usermanagement.db.DatabaseException;
import ua.nure.kh.anton_sushko.usermanagement.util.Messages;


public class DeletePanel extends JPanel implements ActionListener{
	
	private MainFrame parent;
	User user;
	private JPanel buttonPanel;
	private JButton cancelButton;
	private JButton okButton;
	
	public DeletePanel(MainFrame parent) {
		this.parent = parent;
		parent.setSize(400,200);
		initialize();
	}

	
	private void initialize() {
		this.setName("deletePanel");
		this.setLayout(new BorderLayout());
		this.add(getButtonPanel(), BorderLayout.SOUTH);
		
	}
	
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton(), null);
			buttonPanel.add(getCancelButton(), null);
			}
		return buttonPanel;
	}

	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(Messages.getString("AddPanel.cancel"));
			cancelButton.setName("cancelButton");
			cancelButton.setActionCommand("cancel");
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText(Messages.getString("AddPanel.ok"));
			okButton.setName("okButton");
			okButton.setActionCommand("ok");
			okButton.addActionListener(this);
		}
		return okButton;
	}
	public void showDeletePanel(User find) {
		 this.user = find;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if("ok".equalsIgnoreCase(e.getActionCommand())) {
			try {
				parent.getDao().delete(user);
			} catch (DatabaseException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		this.setVisible(false);
		parent.setSize(800,600);
		parent.showBrowsePanel();
	
	}
	}