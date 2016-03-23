package com.adenisov.jdbc.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.adenisov.jdbc.dao.operations.UserConnect;
import com.adenisov.jdbc.model.User;
import com.adenisov.jdbc.ui.frame.MainFrame;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class ChangeUserPasswordDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private UserConnect userConnect;
	private MainFrame parent;
	private User user;
	
	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirm;
	private JLabel labelUsernameValue;

	/**
	 * Create the dialog.
	 */
	public ChangeUserPasswordDialog() {
		setTitle("Change Password");
		setResizable(false);
		setBounds(100, 100, 250, 150);
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
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblUsername = new JLabel("Username:");
			lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblUsername, "2, 2");
		}
		{
			labelUsernameValue = new JLabel("");
			labelUsernameValue.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(labelUsernameValue, "4, 2");
		}
		{
			JLabel lblPassword = new JLabel("Password:");
			contentPanel.add(lblPassword, "2, 4, right, default");
		}
		{
			passwordField = new JPasswordField();
			contentPanel.add(passwordField, "4, 4, fill, default");
		}
		{
			JLabel lblConfirmPassword = new JLabel("Confirm Password:");
			contentPanel.add(lblConfirmPassword, "2, 6, right, default");
		}
		{
			passwordFieldConfirm = new JPasswordField();
			contentPanel.add(passwordFieldConfirm, "4, 6, fill, default");
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
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public ChangeUserPasswordDialog(MainFrame parent, User user) {
		this();
		this.parent = parent;
		this.user = user;
		setLocationRelativeTo(parent);
	}

	private void showErrorMessage(String message, String title) {
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}

	private boolean isPasswordEquals() {
		String password = new String(passwordField.getPassword());
		String confirmPassword = new String(passwordFieldConfirm.getPassword());
		
		if (!password.equals(confirmPassword)) {
			showErrorMessage("Passwords do not match!", "Error changing password");
			return false;
		}
		user.setPassword(password);
		return true;
	}

	protected void save() {
		if (!isPasswordEquals()) {
			return;
		}
		try {
			userConnect = new UserConnect();
			userConnect.changePassword(user);
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Updating Password");
		}
		setVisible(false);
		parent.showInfoMessage("Password updated successfully!", "Password Updated");
	}

	public void setUsernameLabelText(String userFullName) {
		labelUsernameValue.setText(userFullName);
	}

}
