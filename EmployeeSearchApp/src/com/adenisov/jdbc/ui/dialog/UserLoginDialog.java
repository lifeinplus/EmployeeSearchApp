package com.adenisov.jdbc.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.adenisov.jdbc.dao.operations.UserConnect;
import com.adenisov.jdbc.model.User;
import com.adenisov.jdbc.ui.frame.MainFrame;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class UserLoginDialog extends JDialog {

	private static final long serialVersionUID = 3235881993498183626L;

	private UserConnect userConnect;
	
	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	private JComboBox<Object> userComboBox;

	/**
	 * Create the dialog.
	 */
	public UserLoginDialog() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setTitle("User Login");
		setBounds(100, 100, 225, 125);
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
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblUser = new JLabel("Username:");
			contentPanel.add(lblUser, "2, 2, left, default");
		}
		{
			userComboBox = new JComboBox<Object>();
			contentPanel.add(userComboBox, "4, 2");
		}
		{
			JLabel lblPassword = new JLabel("Password:");
			contentPanel.add(lblPassword, "2, 4, left, default");
		}
		{
			passwordField = new JPasswordField();
			contentPanel.add(passwordField, "4, 4, fill, default");
			addWindowListener(new WindowAdapter() {
			    public void windowOpened(WindowEvent e ){
			    	passwordField.requestFocusInWindow();
			    }
			});
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						login();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
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

	public UserLoginDialog(UserConnect userConnect) {
		this();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.userConnect = userConnect;
	}

	private void showMainWindow(User user) {
		MainFrame app = new MainFrame(user.getId(), user.isAdmin());
		app.setLoggedInUserName(user.getFullName());
		app.setVisible(true);
	}

	private void showErrorMessage(String message, String title) {
		JOptionPane.showMessageDialog(this,
				message, title, JOptionPane.ERROR_MESSAGE);
	}

	protected void login() {
		
		User user = (User) userComboBox.getSelectedItem();
		String plainPassword = new String(passwordField.getPassword());
		
		try {
			boolean correctPassword = userConnect.authenticate(user.getId(), plainPassword);
			
			if (correctPassword) {
				setVisible(false);
				dispose();
				showMainWindow(user);
			} else {
				showErrorMessage("Incorrect Password!", "Invalid Login");
			}
		} catch (SQLException e) {
			showErrorMessage(e.getMessage(), "Error Authentication");
		}
	}

	public void populateUsers(List<User> users) {
		userComboBox.setModel(new DefaultComboBoxModel<Object>(users.toArray(new User[0])));
	}

}
