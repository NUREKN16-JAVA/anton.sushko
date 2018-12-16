package ua.nure.kh.anton_sushko.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kh.anton_sushko.usermanagement.User;
import ua.nure.kh.anton_sushko.usermanagement.db.DatabaseException;
import ua.nure.kh.anton_sushko.usermanagement.util.Messages;

public class AddPanel extends JPanel implements ActionListener {
	private  Color bgColor = Color.WHITE;
	private MainFrame parent;
	private JPanel buttonPanel;
	private JPanel fieldPanel;
	private JButton cancelButton;
	private JButton okButton;
	private JTextField birthdateField;
	private JTextField lastNameField;
	private JTextField firstNameField;

	public AddPanel(MainFrame parent) {
		this.parent= parent;
		initialize();
	}
	
	private void initialize() {
		this.setName("addPanel");
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(),BorderLayout.NORTH);
		this.add(getButtonPanel(),BorderLayout.SOUTH);
	
	}
	private JPanel getButtonPanel() {
		if(buttonPanel==null) {
			buttonPanel= new JPanel();
			buttonPanel.add(getOkButton(),null);
			buttonPanel.add(getCancelButton(),null);
		} 
		return buttonPanel;
	}
	private JButton getCancelButton() {
		if(cancelButton==null) {
			cancelButton= new JButton();
			cancelButton.setText(Messages.getString("AddPanel.cancel"));
			cancelButton.setName("cancelButton");
		    cancelButton.setActionCommand("ok");
		    cancelButton.addActionListener(this);
		}
		
		return cancelButton;
	}
	private JButton getOkButton() {
		if(okButton==null) {
			okButton= new JButton();
			okButton.setText(Messages.getString("AddPanel.ok"));
			okButton.setName("okButton");
			okButton.setActionCommand("ok");
			okButton.addActionListener(this);
		}
			return okButton;
	}
	private JPanel getFieldPanel() {
		if(fieldPanel==null) {
			fieldPanel= new JPanel();
			fieldPanel.setLayout(new GridLayout(3, 2));
			addLabeledField(fieldPanel,Messages.getString("AddPanel.first_name"),getFirstNameField());
			addLabeledField(fieldPanel,Messages.getString("AddPanel.last_name"),getLastNameField());
			addLabeledField(fieldPanel,Messages.getString("AddPanel.date_of_birth"),getbirthdateField());
			
		} 
		return fieldPanel;
	}
	private JTextField getbirthdateField() {
		if(birthdateField==null) {
			birthdateField= new JTextField();
			birthdateField.setName("birthdateField");
			
		}
		return birthdateField;
	}
	private JTextField getLastNameField() {
		if(lastNameField==null) {
			lastNameField= new JTextField();
			lastNameField.setName("lastNameField");
			
		}
		return lastNameField;
	}
	private JTextField getFirstNameField() {
		if(firstNameField==null) {
			firstNameField= new JTextField();
			firstNameField.setName("firstNameField");
			
		}
		return firstNameField;
	}
	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
	JLabel label=new JLabel(labelText);
	label.setLabelFor(textField);
	panel.add(label);
	panel.add(textField);
	
	
		
}


@Override
public void actionPerformed(ActionEvent e) {
	if("ok".equalsIgnoreCase(e.getActionCommand())) {
		User user= new User();
		user.setFirstname(getFirstNameField().getText());
		user.setLastname(getLastNameField().getText());
		DateFormat format = DateFormat.getDateInstance();
		try {
			user.setBirthdate(format.parse(getbirthdateField().getText()));
		} catch (ParseException e1) {
			getbirthdateField().setBackground(Color.RED);
			return;
		}
		
		try {
			parent.getDao().create(user);
		} catch (DatabaseException e1) {
		JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
		}
	}
	clearFields();
	this.setVisible(false);
	parent.showBrowsePanel();
}
private void clearFields() {
	getFirstNameField().setText("");
	getFirstNameField().setBackground(bgColor);
	
	getLastNameField().setText("");
	getLastNameField().setBackground(bgColor);
	
	getbirthdateField().setText("");
	getbirthdateField().setBackground(bgColor);
	
}


}
