package ua.nure.kh.anton_sushko.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kh.anton_sushko.usermanagement.User;
import ua.nure.kh.anton_sushko.usermanagement.util.Messages;



public class DetailsPanel extends JPanel implements ActionListener{
	
	private MainFrame parent;
	private JPanel buttonPanel;
	private JPanel fieldPanel;
	private JButton okButton;
	private JTextField birthdateField;
	private JTextField lastNameField;
	private JTextField firstNameField;

	public DetailsPanel(MainFrame frame) {
		this.parent = frame;
		initialize();
	}

	private void initialize() {
		this.setName("detailsPanel");
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.CENTER);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
	}
	
	
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton(), null);
			
			}
		return buttonPanel;
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

	private JPanel getFieldPanel() {
		if (fieldPanel == null) {
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridLayout(3, 2));
			addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField());
			addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField());
			addLabeledField(fieldPanel, Messages.getString("AddPanel.date_of_birth"), getbirthdateField());
		
		}
		return fieldPanel;
	}

	

	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		JLabel label = new JLabel(labelText);
		textField.setEditable(false);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);
		
	}

	private JTextField getFirstNameField() {
		if (firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setName("firstNameField");
		}
		return firstNameField;
	}
	
	private JTextField getbirthdateField() {
		if (birthdateField == null) {
			birthdateField = new JTextField();
			birthdateField.setName("birthdateField");
		}
		return birthdateField;
	}

	private JTextField getLastNameField() {
		if (lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setName("lastNameField");
		}
		return lastNameField;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if("ok".equalsIgnoreCase(e.getActionCommand())) {
			this.setVisible(false);
			parent.showBrowsePanel();
		        		
		}
		
	}

	public void showDetailsPanel(User user) {
		
        getFirstNameField().setText(user.getFirstname());
        getLastNameField().setText(user.getLastname());
        DateFormat dateFormat = DateFormat.getDateInstance();
        getbirthdateField().setText(dateFormat.format(user.getBirthdate()));
	}
	

}