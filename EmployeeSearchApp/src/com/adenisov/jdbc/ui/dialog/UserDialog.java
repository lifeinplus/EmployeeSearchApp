package com.adenisov.jdbc.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.adenisov.jdbc.dao.operations.UserConnect;
import com.adenisov.jdbc.model.User;
import com.adenisov.jdbc.ui.frame.MainFrame;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class UserDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private MainFrame parent;
	private UserConnect userConnect;
	private User user;
	private boolean updateMode = true;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldEmail;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirm;
	private JCheckBox checkBoxAdmin;
	private JLabel lblPassword;
	private JLabel lblConfirmPassword;


	/**
	 * Create the dialog.
	 */
	public UserDialog() {
		setTitle("Update User");
		setResizable(false);
		setBounds(100, 100, 250, 240);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,}));
		{
			JLabel lblFirstName = new JLabel("First Name:");
			contentPanel.add(lblFirstName, "2, 2, right, default");
		}
		{
			textFieldFirstName = new JTextField();
			contentPanel.add(textFieldFirstName, "4, 2, fill, default");
			textFieldFirstName.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name:");
			contentPanel.add(lblLastName, "2, 4, right, default");
		}
		{
			textFieldLastName = new JTextField();
			contentPanel.add(textFieldLastName, "4, 4, fill, default");
			textFieldLastName.setColumns(10);
		}
		{
			JLabel lblEmail = new JLabel("Email:");
			contentPanel.add(lblEmail, "2, 6, right, default");
		}
		{
			textFieldEmail = new JTextField();
			contentPanel.add(textFieldEmail, "4, 6, fill, default");
			textFieldEmail.setColumns(10);
		}
		{
			JLabel lblAdmin = new JLabel("Admin:");
			contentPanel.add(lblAdmin, "2, 8, right, default");
		}
		{
			checkBoxAdmin = new JCheckBox("");
			contentPanel.add(checkBoxAdmin, "4, 8");
		}
		{
			lblPassword = new JLabel("Password:");
			contentPanel.add(lblPassword, "2, 10, right, default");
		}
		{
			passwordField = new JPasswordField();
			contentPanel.add(passwordField, "4, 10, fill, default");
		}
		{
			lblConfirmPassword = new JLabel("Confirm Password:");
			contentPanel.add(lblConfirmPassword, "2, 12, right, default");
		}
		{
			passwordFieldConfirm = new JPasswordField();
			contentPanel.add(passwordFieldConfirm, "4, 12, fill, default");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						save();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public UserDialog(MainFrame parent) {
		this(parent, null);
	}

	public UserDialog(MainFrame parent, User user) {
		this();
		this.parent = parent;
		
		if (user == null) {
			this.user = new User();
			this.updateMode = false;
			setTitle("Add User");
		} else {
			this.user = user;
			populateFields(user);
			hidePasswordFields();
		}
		setLocationRelativeTo(parent);
	}

	private void hidePasswordFields() {
		lblPassword.setVisible(false);
		passwordField.setVisible(false);
		lblConfirmPassword.setVisible(false);
		passwordFieldConfirm.setVisible(false);
		setBounds(100, 100, 250, 175);
	}

	private void populateFields(User user) {
		textFieldFirstName.setText(user.getFirstName());
		textFieldLastName.setText(user.getLastName());
		textFieldEmail.setText(user.getEmail());
		checkBoxAdmin.setSelected(user.isAdmin());
	}

	private boolean isPasswordsEquals() {
		String password = new String(passwordField.getPassword());
		String confirmPassword = new String(passwordFieldConfirm.getPassword());
		
		if (!password.equals(confirmPassword)) {
			showErrorMessage("Passwords do not match!", "Error changing password");
			return false;
		}
		user.setPassword(password);
		return true;
	}

	private void showErrorMessage(String message, String title) {
		JOptionPane.showMessageDialog(this,
				message, title, JOptionPane.ERROR_MESSAGE);
	}

	protected void save() {
		
		user.setFirstName(textFieldFirstName.getText());
		user.setLastName(textFieldLastName.getText());
		user.setEmail(textFieldEmail.getText());
		user.setAdmin(checkBoxAdmin.isSelected());
		
		try {
			userConnect = new UserConnect();
			if (updateMode) {
				userConnect.updateUser(user);
			} else {
				if (!isPasswordsEquals()) {
					return;
				}
				userConnect.addUser(user);
			}
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Saving User");
		}

		setVisible(false);
		dispose();
		parent.populateUsersTable();
		parent.showInfoMessage("User \"" + user.getFullName() + "\" saved successfully!", "User Saved");
	}

}
