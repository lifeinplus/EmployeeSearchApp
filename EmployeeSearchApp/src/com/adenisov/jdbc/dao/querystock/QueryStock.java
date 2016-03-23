package com.adenisov.jdbc.dao.querystock;

public class QueryStock {

	/**
	 * SELECT * FROM employees<br/>
	 * ORDER BY last_name;<br/>
	 */
	public String getSelectAllEmployeesQuery() {
		return "SELECT * FROM employees ORDER BY last_name;";
	}
	
	/**
	 * SELECT * FROM employees<br/>
	 * WHERE last_name like ?<br/>
	 * ORDER BY last_name;
	 */
	public String getSelectAllLikeLastNameQuery() {
		return "SELECT * FROM employees WHERE last_name like ? ORDER BY last_name;";
	}

	/**
	 * SELECT * FROM employees<br/>
	 * WHERE department = ?<br/>
	 * ORDER BY last_name;
	 */
	public String getSelectAllLikeDepartmentQuery() {
		return "SELECT * FROM employees WHERE department like ? ORDER BY last_name;";
	}

	/**
	 * SELECT * FROM employees<br/>
	 * WHERE last_name like ? and department like ?<br/>
	 * ORDER BY last_name;
	 */
	public String getSelectAllLikeLastNameAndDepartmentQuery() {
		return "SELECT * FROM employees WHERE last_name like ? and department like ? ORDER BY last_name;";
	}

	/**
	 * SELECT department FROM employees<br/>
	 * GROUP BY department;
	 */
	public String getSelectDepartmentsQuery() {
		return "SELECT department FROM employees GROUP BY department;";
	}

	/**
	 * SELECT pdf_resume FROM employees<br/>
	 * WHERE email = ?;
	 */
	public String getSelectPdfResumeByEmailQuery() {
		return "SELECT pdf_resume FROM employees WHERE email = ?;";
	}

	/**
	 * SELECT txt_resume FROM employees<br/>
	 * WHERE email = ?;
	 */
	public String getSelectTxtResumeByEmailQuery() {
		return "SELECT txt_resume FROM employees WHERE email = ?;";
	}

	/**
	 * INSERT INTO employees (first_name, last_name, email, department, salary)<br/>
	 * VALUES (?, ?, ?, ?, ?);
	 */
	public String getInsertEmployeeQuery() {
		return "INSERT INTO employees (first_name, last_name, email, department, salary) VALUES (?, ?, ?, ?, ?);";
	}

	/**
	 * UPDATE employees<br/>
	 * SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ?<br/>
	 * WHERE id = ?;
	 */
	public String getUpdateEmployeeQuery() {
		return "UPDATE employees SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ? WHERE id = ?;";
	}

	/** UPDATE employees SET salary = ? WHERE department = ?; */
	public String getUpdateSalaryByDepartmentQuery() {
		return "UPDATE employees SET salary = ? WHERE department = ?;";
	}

	/** UPDATE employees SET pdf_resume = ? WHERE email = ?; */
	public String getUpdatePdfResumeByEmailQuery() {
		return "UPDATE employees SET pdf_resume = ? WHERE email = ?;";
	}

	/** UPDATE employees SET txt_resume = ? WHERE email = ?; */
	public String getUpdateTxtResumeByEmailQuery() {
		return "UPDATE employees SET txt_resume = ? WHERE email = ?;";
	}

	/** DELETE FROM employees WHERE id = ?; */
	public String getDeleteEmployeeQuery() {
		return "DELETE FROM employees WHERE id = ?;";
	}

	/** DELETE FROM employees WHERE department = ?; */
	public String getDeleteByDepartmentQuery() {
		return "DELETE FROM employees WHERE department = ?;";
	}

	/** {call greet_the_department(?)}; */
	public String getDepartmentGreetingProcedure() {
		return "{call greet_the_department(?)};";
	}

	/** {call get_count_for_department(?, ?)}; */
	public String getCountEmployeesProcedure() {
		return "{call get_count_for_department(?, ?)};";
	}

	/** {call increase_salaries_for_department(?, ?)}; */
	public String getIncreaseSalariesProcedure() {
		return "{call increase_salaries_for_department(?, ?)};";
	}

}
