package model;

import java.sql.SQLException;

import com.fasterxml.jackson.databind.JsonNode;

import dao.StudentDAO;

public class Student {

	private String userName;
	
	private String firstName;
	
	private String lastName;
	
	private String age;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	/**
	 * This method saves the student information
	 * @param student
	 * @throws SQLException
	 */
	public static void saveStudent(JsonNode student) throws SQLException {
		Student st = new Student();
		st.setUserName(student.findPath("userName").textValue());
		st.setFirstName(student.findPath("firstName").textValue());
		st.setLastName(student.findPath("lastName").textValue());
		st.setAge(student.findPath("age").textValue());
		StudentDAO.saveStudent(st);
	}
	
	/**
	 * This method gets the student information.
	 * @param username
	 * @return Student
	 * @throws SQLException
	 */
	public static Student getStudent(String username) throws SQLException {
		return StudentDAO.getStudent(username);
	}
}
