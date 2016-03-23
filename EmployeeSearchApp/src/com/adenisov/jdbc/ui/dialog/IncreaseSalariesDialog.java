package com.adenisov.jdbc.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.adenisov.jdbc.dao.operations.EmployeeConnect;
import com.adenisov.jdbc.ui.frame.MainFrame;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class IncreaseSalariesDialog extends JDialog {

	private static final long serialVersionUID = 6884821767904179415L;
	
	private MainFrame parent;
	private EmployeeConnect employeeConnect;
	private int userId;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField increaseAmountTextField;
	private JLabel lblIncreaseAmount;
	private JComboBox<Object> departmentComboBox;

	/**
	 * Create the dialog.
	 */
	public IncreaseSalariesDialog() {
		setResizable(false);
		setTitle("Increase Salaries");
		setBounds(100, 100, 250, 125);
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
			JLabel lblDepartment = new JLabel("Department:");
			contentPanel.add(lblDepartment, "2, 2, left, center");
		}
		{
			departmentComboBox = new JComboBox<>();
			contentPanel.add(departmentComboBox, "4, 2");
		}
		{
			lblIncreaseAmount = new JLabel("Increase amount:");
			contentPanel.add(lblIncreaseAmount, "2, 4, left, center");
		}
		{
			increaseAmountTextField = new JTextField();
			contentPanel.add(increaseAmountTextField, "4, 4, fill, top");
			increaseAmountTextField.setColumns(10);
			addWindowListener(new WindowAdapter() {
			    public void windowOpened(WindowEvent e ){
			        increaseAmountTextField.requestFocusInWindow();
			    }
			});
		}
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						increase();
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

	public IncreaseSalariesDialog(MainFrame parent, int userId, String department) {
		this();
		this.parent = parent;
		this.userId = userId;
		setLocationRelativeTo(parent);
		
		try {
			employeeConnect = new EmployeeConnect();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		populateDepartmentComboBox();
		selectPassedDepartment(department);
	}

	private void selectPassedDepartment(String department) {
		departmentComboBox.setSelectedItem(department);
	}

	private void populateDepartmentComboBox() {
		try {
			ArrayList<String> departments = employeeConnect.getDepartments();
			departmentComboBox.setModel(new DefaultComboBoxModel<Object>(departments.toArray()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void increase() {
		String department = (String) departmentComboBox.getSelectedItem();
		double increaseAmount = Double.parseDouble(increaseAmountTextField.getText());
		
		try {
			employeeConnect.increaseSalaries(userId, department, increaseAmount);
		} catch (SQLException | ClassNotFoundException | IOException e) {
			parent.showErrorMessage(e.getMessage(), "Error Increasing Salaries");
		}
		
		setVisible(false);
		dispose();
		parent.populateEmployeesTable();
		parent.showInfoMessage("Salaries for " + department + " department increased successfully!", "Salaries Increased");
	}

}
