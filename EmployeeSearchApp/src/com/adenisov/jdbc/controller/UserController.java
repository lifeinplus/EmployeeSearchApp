package com.adenisov.jdbc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.adenisov.jdbc.dao.operations.UserConnect;
import com.adenisov.jdbc.model.User;
import com.adenisov.jdbc.ui.dialog.ChangeUserPasswordDialog;
import com.adenisov.jdbc.ui.dialog.UserDialog;
import com.adenisov.jdbc.ui.frame.MainFrame;

public class UserController {

	private MainFrame parent;
	private UserConnect userConnect;
	
	public UserController(MainFrame parent) {
		this.parent = parent;
	}

	public List<User> getUsers(int userId, boolean admin) {
		List<User> users = null;
		try {
			userConnect = new UserConnect();
			if (admin) {
				users = userConnect.getAllUsers();
			} else {
				users = userConnect.getUserById(userId);
			}
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Retrieving Users");
		}
		return users;
	}

	public void addUser() {
		UserDialog dialog = new UserDialog(parent);
		dialog.setVisible(true);
	}

	public void updateUser(User user) {
		UserDialog dialog = new UserDialog(parent, user);
		dialog.setVisible(true);
	}

	public void changeUserPassword(User user) {
		ChangeUserPasswordDialog dialog = new ChangeUserPasswordDialog(parent, user);
		dialog.setUsernameLabelText(user.getFullName());
		dialog.setVisible(true);
	}

	public void deleteUser(User user) {
		try {
			userConnect = new UserConnect();
			userConnect.deleteUser(user.getId());
			parent.showInfoMessage("User \"" + user.getFullName() + "\" deleted successfully!", "User Deleted");
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Deleting User");
		}
	}

}
