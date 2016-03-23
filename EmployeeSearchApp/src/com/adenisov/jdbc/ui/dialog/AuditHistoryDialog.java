package com.adenisov.jdbc.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.adenisov.jdbc.model.AuditHistory;
import com.adenisov.jdbc.ui.frame.MainFrame;
import com.adenisov.jdbc.ui.renderer.DateTimeCellRenderer;
import com.adenisov.jdbc.ui.tablemodel.AuditHistoryTableModel;

public class AuditHistoryDialog extends JDialog {

	private static final long serialVersionUID = -8403251470715678187L;

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JLabel lblEmployeeAuditHistory;

	/**
	 * Create the dialog.
	 */
	public AuditHistoryDialog() {
		setTitle("Audit History");
		setResizable(false);
		setBounds(100, 100, 400, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			{
				JLabel lblAuditHistoryFor = new JLabel("Audit History for Employee:");
				panel.add(lblAuditHistoryFor);
			}
			{
				lblEmployeeAuditHistory = new JLabel("New label");
				panel.add(lblEmployeeAuditHistory);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	public AuditHistoryDialog(MainFrame parent) {
		this();
		setLocationRelativeTo(parent);
	}

	private void setEmployeeLabelText(String employeeFullName) {
		lblEmployeeAuditHistory.setText(employeeFullName);
	}

	private void setColumnsWidth() {
		TableColumnModel columnModel = table.getColumnModel();
		int widths[] = { 80, 90, 50 };
		for (int i = 0; i < widths.length; i++) {
			columnModel.getColumn(i).setPreferredWidth(widths[i]);
		}
	}

	private void setCellRenderer() {
		TableCellRenderer cellRenderer = new DateTimeCellRenderer();
		table.getColumnModel()
			.getColumn(AuditHistoryTableModel.ACTION_DATE_TIME_COLUMN)
			.setCellRenderer(cellRenderer);
	}

	private void populateTable(List<AuditHistory> auditHistoryList) {
		AuditHistoryTableModel model = new AuditHistoryTableModel(auditHistoryList);
		table.setModel(model);
		setColumnsWidth();
		setCellRenderer();
	}

	public void populate(String employeeFullName, List<AuditHistory> auditHistoryList) {
		setEmployeeLabelText(employeeFullName);
		populateTable(auditHistoryList);
	}

}
