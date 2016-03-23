package com.adenisov.jdbc.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.adenisov.jdbc.dao.operations.EmployeeConnect;
import com.adenisov.jdbc.model.Employee;
import com.adenisov.jdbc.ui.frame.MainFrame;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class EmployeeDialog extends JDialog {

	private static final long serialVersionUID = -5524806742506547666L;
	
	private MainFrame parent;
	private EmployeeConnect employeeConnect;
	private Employee employee;
	private boolean updateMode = false;
	private int userId;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField emailTextField;
	private JTextField departmentTextField;
	private JTextField salaryTextField;

	/**
	 * Create the dialog.
	 */
	public EmployeeDialog() {
		setTitle("Add Employee");
		setResizable(false);
		setBounds(100, 100, 250, 210);
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
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblFirstName = new JLabel("First Name:");
			contentPanel.add(lblFirstName, "2, 2, right, default");
		}
		{
			firstNameTextField = new JTextField();
			contentPanel.add(firstNameTextField, "4, 2, fill, default");
			firstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name:");
			contentPanel.add(lblLastName, "2, 4, right, default");
		}
		{
			lastNameTextField = new JTextField();
			contentPanel.add(lastNameTextField, "4, 4, fill, default");
			lastNameTextField.setColumns(10);
		}
		{
			JLabel lblEmail = new JLabel("Email:");
			contentPanel.add(lblEmail, "2, 6, right, default");
		}
		{
			emailTextField = new JTextField();
			contentPanel.add(emailTextField, "4, 6, fill, default");
			emailTextField.setColumns(10);
		}
		{
			JLabel lblDepartment = new JLabel("Department:");
			contentPanel.add(lblDepartment, "2, 8, right, default");
		}
		{
			departmentTextField = new JTextField();
			contentPanel.add(departmentTextField, "4, 8, fill, default");
			departmentTextField.setColumns(10);
		}
		{
			JLabel lblSalary = new JLabel("Salary:");
			contentPanel.add(lblSalary, "2, 10, right, default");
		}
		{
			salaryTextField = new JTextField();
			contentPanel.add(salaryTextField, "4, 10, fill, default");
			salaryTextField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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

	public EmployeeDialog(MainFrame parent, int userId) {
		this(parent, userId, null);
	}

	public EmployeeDialog(MainFrame parent, int userId, Employee employee) {
		this();
		this.parent = parent;
		this.employee = employee;
		this.userId = userId; // используется для внесения данных в табилцу audit_history
		setLocationRelativeTo(parent);
		
		if (employee != null) {
			setTitle("Update Employee");
			updateMode = true;
			populateGui(employee);
		}
	}

	private void populateGui(Employee employee) {
		firstNameTextField.setText(employee.getFirstName());
		lastNameTextField.setText(employee.getLastName());
		emailTextField.setText(employee.getEmail());
		departmentTextField.setText(employee.getDepartment());
		salaryTextField.setText(String.valueOf(employee.getSalary()));
	}

	private BigDecimal convertStringToDouble(String stringSalary) {
		BigDecimal bigDecimalSalary = null;
		try {
			double doubleSalary = Double.parseDouble(stringSalary);
			bigDecimalSalary = BigDecimal.valueOf(doubleSalary);
		} catch (Exception e) {
			System.out.println("Invalid salary value. Defaulting to 0.0");
			bigDecimalSalary = BigDecimal.valueOf(0.0);
		}
		return bigDecimalSalary;
	}

	protected void save() {
		
		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		String email = emailTextField.getText();
		String department = departmentTextField.getText();
		BigDecimal salary = convertStringToDouble(salaryTextField.getText());
		
		Employee employee = null;
		
		try {
			employeeConnect = new EmployeeConnect();
			if (updateMode) {
				employee = new Employee(this.employee.getId(), firstName, lastName, email, department, salary);
				employeeConnect.updateEmployee(userId, employee);
			} else {
				employee = new Employee(firstName, lastName, email, department, salary);
				employeeConnect.addEmployee(userId, employee);
			}
			setVisible(false);
			dispose();
			parent.populateEmployeesTable();
			parent.showInfoMessage("Employee \"" + employee.getFullName() + "\" saved successfully!", "Employee Saved");
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Saving Employee");
		}
	}

}
