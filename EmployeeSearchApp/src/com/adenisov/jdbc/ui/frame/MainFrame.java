package com.adenisov.jdbc.ui.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.adenisov.jdbc.controller.ColumnsInfoController;
import com.adenisov.jdbc.controller.EmployeeController;
import com.adenisov.jdbc.controller.UserController;
import com.adenisov.jdbc.dao.operations.UserConnect;
import com.adenisov.jdbc.model.ColumnInfo;
import com.adenisov.jdbc.model.Employee;
import com.adenisov.jdbc.model.User;
import com.adenisov.jdbc.ui.dialog.UserLoginDialog;
import com.adenisov.jdbc.ui.tablemodel.ColumnsInfoTableModel;
import com.adenisov.jdbc.ui.tablemodel.EmployeesTableModel;
import com.adenisov.jdbc.ui.tablemodel.UsersTableModel;

public class MainFrame extends JFrame {	

	private static final long serialVersionUID = 1L;

	private static final int EMPLOYEES_TAB_INDEX = 0;
	private static final int COLUMNS_INFO_TAB_INDEX = 1;
	private static final int USERS_TAB_INDEX = 2;
	
	private EmployeeController employeeController;
	private ColumnsInfoController columnsInfoController;
	private UserController userController;
	
	private int userId;
	private boolean admin;
	
	private int selectedEmployeeRow;
	private int selectedUserRow;
	
	private JTextField textFieldDepartment;
	private JTextField textFieldLastName;
	private JTable tableEmployees;
	private JTable tableColumnsInfo;
	private JTable tableUsers;
	private JLabel lblCountEmployees;
	private JLabel lblLoggedInUsername;
	private JButton btnAddUser;
	private JButton btnDeleteUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					UserConnect userConnect = new UserConnect();
					List<User> users = userConnect.getAllUsers();
					
					UserLoginDialog dialog = new UserLoginDialog(userConnect);
					dialog.populateUsers(users);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Employee Search App");
		setResizable(false);
		setBounds(100, 100, 600, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				textFieldLastName.requestFocusInWindow();
			}
		});
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelInfo = new JPanel();
		contentPane.add(panelInfo, BorderLayout.NORTH);
		panelInfo.setLayout(new BorderLayout(0, 0));
		
		JPanel panelLoginInfo = new JPanel();
		panelInfo.add(panelLoginInfo);
		FlowLayout fl_panelLoginInfo = new FlowLayout(FlowLayout.LEFT, 5, 7);
		panelLoginInfo.setLayout(fl_panelLoginInfo);
		
		JLabel lblLoggedIn = new JLabel("Logged In:");
		panelLoginInfo.add(lblLoggedIn);
		
		lblLoggedInUsername = new JLabel("");
		panelLoginInfo.add(lblLoggedInUsername);
		
		JPanel panelInfoButtons = new JPanel();
		panelInfo.add(panelInfoButtons, BorderLayout.EAST);
		
		JButton btnDatabaseInfo = new JButton("Database Info");
		panelInfoButtons.add(btnDatabaseInfo);
		btnDatabaseInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDatabaseInfo();
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				setFrameSize(selectedIndex);
			}
		});
		
		JPanel panelEmployees = new JPanel();
		tabbedPane.addTab("Employees", null, panelEmployees, null);
		panelEmployees.setLayout(new BorderLayout(0, 0));
		
		/*
		 * Search Button
		 */
		
		JPanel panelSearch = new JPanel();
		panelEmployees.add(panelSearch, BorderLayout.NORTH);
		panelSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JLabel lblLastName = new JLabel("Last Name:");
		panelSearch.add(lblLastName);
		
		textFieldLastName = new JTextField();
		panelSearch.add(textFieldLastName);
		textFieldLastName.setColumns(8);
		textFieldLastName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				populateEmployeesTable();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				populateEmployeesTable();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				populateEmployeesTable();
			}
		});
		
		JLabel lblDepartment = new JLabel("Department:");
		panelSearch.add(lblDepartment);
		
		textFieldDepartment = new JTextField();
		panelSearch.add(textFieldDepartment);
		textFieldDepartment.setColumns(8);
		textFieldDepartment.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				populateEmployeesTable();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				populateEmployeesTable();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				populateEmployeesTable();
			}
		});
		
		JScrollPane scrollPaneEmployees = new JScrollPane();
		panelEmployees.add(scrollPaneEmployees);
		
		tableEmployees = new JTable();
		tableEmployees.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
		    public void valueChanged(ListSelectionEvent event) {
				countEmployees();
		    }
		});
		setTableHeaderAlignment(tableEmployees);
		scrollPaneEmployees.setViewportView(tableEmployees);
		
		JPanel panelSouth = new JPanel();
		panelEmployees.add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCountDepartment = new JPanel();
		FlowLayout fl_panelCountDepartment = (FlowLayout) panelCountDepartment.getLayout();
		fl_panelCountDepartment.setVgap(10);
		panelSouth.add(panelCountDepartment, BorderLayout.WEST);
		
		JLabel lblInDepartment = new JLabel("In Departmen:");
		panelCountDepartment.add(lblInDepartment);
		
		lblCountEmployees = new JLabel("");
		panelCountDepartment.add(lblCountEmployees);
		
		JPanel panelEmployeeButtons = new JPanel();
		FlowLayout fl_panelEmployeeButtons = (FlowLayout) panelEmployeeButtons.getLayout();
		fl_panelEmployeeButtons.setAlignment(FlowLayout.RIGHT);
		panelSouth.add(panelEmployeeButtons, BorderLayout.EAST);
		
		JButton btnAddEmployee = new JButton("Add");
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEmployee();
			}
		});
		panelEmployeeButtons.add(btnAddEmployee);
		
		JButton btnUpdateEmployee = new JButton("Update");
		btnUpdateEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateEmployee();
			}
		});
		panelEmployeeButtons.add(btnUpdateEmployee);
		
		JButton btnDeleteEmployee = new JButton("Delete");
		btnDeleteEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteEmployee();
			}
		});
		panelEmployeeButtons.add(btnDeleteEmployee);
		
		JButton btnHistory = new JButton("History");
		btnHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewHistory();
			}
		});
		panelEmployeeButtons.add(btnHistory);
		
		JButton btnGreet = new JButton("Greet");
		btnGreet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				greetDepartment();
			}
		});
		panelEmployeeButtons.add(btnGreet);
		
		JButton btnIncrease = new JButton("Increase");
		btnIncrease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				increaseSalaries();
			}
		});
		panelEmployeeButtons.add(btnIncrease);
		
		JPanel panelColumnsInfo = new JPanel();
		tabbedPane.addTab("Columns Info", null, panelColumnsInfo, null);
		panelColumnsInfo.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelColumnsInfo.add(scrollPane);
		
		tableColumnsInfo = new JTable();
		setTableHeaderAlignment(tableColumnsInfo);
		scrollPane.setViewportView(tableColumnsInfo);
		
		JPanel panelUsers = new JPanel();
		tabbedPane.addTab("Users", null, panelUsers, null);
		panelUsers.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneUser = new JScrollPane();
		panelUsers.add(scrollPaneUser, BorderLayout.CENTER);
		
		tableUsers = new JTable();
		tableUsers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
		    public void valueChanged(ListSelectionEvent event) {
				getSelectedRow();
		    }
		});
		setTableHeaderAlignment(tableUsers);
		scrollPaneUser.setViewportView(tableUsers);
		
		JPanel panelUserButtons = new JPanel();
		panelUsers.add(panelUserButtons, BorderLayout.SOUTH);
		
		btnAddUser = new JButton("Add");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addUser();
			}
		});
		panelUserButtons.add(btnAddUser);
		
		JButton btnUpdateUser = new JButton("Update");
		btnUpdateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateUser();
			}
		});
		panelUserButtons.add(btnUpdateUser);
		
		JButton btnChangeUserPassword = new JButton("Change Password");
		btnChangeUserPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeUserPassword();
			}
		});
		
		btnDeleteUser = new JButton("Delete");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteUser();
			}
		});
		panelUserButtons.add(btnDeleteUser);
		panelUserButtons.add(btnChangeUserPassword);
	}

	public MainFrame(int userId, boolean admin) {
		this();
		this.userId = userId;
		this.admin = admin;
		
		employeeController = new EmployeeController(this);
		columnsInfoController = new ColumnsInfoController(this);
		userController = new UserController(this);
		
		btnAddUser.setVisible(admin);
		btnDeleteUser.setVisible(admin);
		
		populateEmployeesTable();
		populateColumnsInfoTable();
		populateUsersTable();
	}

	private void setFrameSize(int selectedIndex) {
		switch (selectedIndex) {
		case EMPLOYEES_TAB_INDEX:
			setSize(600, 366);
			break;
		case COLUMNS_INFO_TAB_INDEX:
			setSize(400, 255);
			break;
		case USERS_TAB_INDEX:
			setSize(450, 240);
			break;
		}
	}

	private void setTableHeaderAlignment(JTable table) {
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}

	private void setColumnsWidth(JTable table, int[] widths) {
		TableColumnModel model = table.getColumnModel();
		for (int i = 0; i < widths.length; i++) {
			model.getColumn(i).setPreferredWidth(widths[i]);
		}
	}

	private void setEmployeeColumnsWidth() {
		int widths[] = { 80, 90, 150, 100, 70 };
		setColumnsWidth(tableEmployees, widths);
	}

	private void setUserColumnsWidth() {
		int widths[] = { 50, 60, 100, 30 };
		setColumnsWidth(tableUsers, widths);
	}

	private void getSelectedRow() {
		selectedUserRow = tableUsers.getSelectedRow();
	}

	private Employee getSelectedEmployee() {
		return (Employee) tableEmployees.getValueAt(selectedEmployeeRow, EmployeesTableModel.EMPLOYEE_OBJECT);
	}

	private String getSelectedDepartment() {
		return tableEmployees.getValueAt(selectedEmployeeRow, EmployeesTableModel.DEPARTMENT_COLUMN).toString();
	}

	private User getSelectedUser() {
		return (User) tableUsers.getValueAt(selectedUserRow, UsersTableModel.USER_OBJECT);
	}

	private List<Employee> getEmployees() {
		String lastName = textFieldLastName.getText();
		String department = textFieldDepartment.getText();
		return employeeController.getEmployees(lastName, department);
	}

	private int showConfirmMessage(String message, String title) {
		return JOptionPane.showConfirmDialog(this, 
				message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	private void showDatabaseInfo() {
		employeeController.showDatabaseInfo();
	}

	private void countEmployees() {
    	selectedEmployeeRow = tableEmployees.getSelectedRow();
        if (selectedEmployeeRow > -1) {
        	String department = getSelectedDepartment();
        	int count = employeeController.getCountEmployees(department);
        	setCountEmployeesText(String.valueOf(count));
        }
	}

	private void viewHistory() {
		Employee employee = getSelectedEmployee();
		employeeController.showAuditHistory(employee);
	}

	private void greetDepartment() {
		String department = getSelectedDepartment();
		employeeController.getDepartmentGreeting(department);
	}

	private void populateColumnsInfoTable() {
		List<ColumnInfo> columnsInfoList = columnsInfoController.getColumnsInfo();
		ColumnsInfoTableModel model = new ColumnsInfoTableModel(columnsInfoList);
		tableColumnsInfo.setModel(model);
	}

	private void selectFirstRow(JTable table) {
		table.getSelectionModel().setSelectionInterval(0, 0);
	}

	private void setCountEmployeesText(String value) {
		lblCountEmployees.setText(value);
	}

	private void addEmployee() {
		employeeController.addEmployee(userId);
	}

	private void addUser() {
		userController.addUser();
	}

	private void updateEmployee() {
		Employee employee = getSelectedEmployee();
		employeeController.updateEmployee(userId, employee);
	}

	private void updateUser() {
		User user = getSelectedUser();
		userController.updateUser(user);
	}

	private void increaseSalaries() {
		String department = getSelectedDepartment();
		employeeController.increaseSalaries(userId, department);
	}

	private void changeUserPassword() {
		User user = getSelectedUser();
		userController.changeUserPassword(user);
	}

	private void deleteEmployee() {
		Employee employee = getSelectedEmployee();
		int response = showConfirmMessage("Delete \"" + employee.getFullName() + "\"?", "Delete Employee");
		if (response == JOptionPane.NO_OPTION) return;
		
		employeeController.deleteEmployee(employee);
		populateEmployeesTable();
	}

	private void deleteUser() {
		User user = getSelectedUser();
		int response = showConfirmMessage("Delete \"" + user.getFullName() + "\"?", "Delete User");
		if (response == JOptionPane.NO_OPTION) return;
		
		userController.deleteUser(user);
		populateUsersTable();
	}

	public void populateEmployeesTable() {
		List<Employee> employees = getEmployees();
		EmployeesTableModel model = new EmployeesTableModel(employees);
		tableEmployees.setModel(model);
		
		/*
		 * Если список сотрудников имеет хотя бы одну запись, выделяем первую строку.
		 * Если список сотрудников пуст, обнуляем счётчик сотрудников.
		 */
		if (employees.size() > 0) {
			selectFirstRow(tableEmployees);
		} else if (lblCountEmployees != null) {
			setCountEmployeesText("0");
		}
		setEmployeeColumnsWidth();
	}

	public void populateUsersTable() {
		List<User> users = userController.getUsers(userId, admin);
		UsersTableModel model = new UsersTableModel(users);
		tableUsers.setModel(model);
		
		if (users.size() > 0) {
			selectFirstRow(tableUsers);
		}
		setUserColumnsWidth();
	}

	public void setLoggedInUserName(String userFullName) {
		lblLoggedInUsername.setText(userFullName);
	}

	public void showInfoMessage(String message, String title) {
		JOptionPane.showMessageDialog(this,
				message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public void showErrorMessage(String message, String title) {
		JOptionPane.showMessageDialog(this,
				message, title, JOptionPane.ERROR_MESSAGE);
	}

}
