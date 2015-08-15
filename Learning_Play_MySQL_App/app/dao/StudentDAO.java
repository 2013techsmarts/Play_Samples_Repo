package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Student;
import play.Logger;
import play.db.DB;

/**
 * The DAO interacts with the database to save/get/delete and update the student information.
 * @author Siva Janapati
 *
 */
public class StudentDAO {

	/**
	 * This method will save the student information into "STUDENT" table
	 * @param student to save
	 * @throws SQLException 
	 */
	public static void saveStudent(Student student) throws SQLException {
		Connection con = null;
		try {
			con = DB.getConnection();
			Logger.debug("The student information is getting saved");
			String insertStudentSQL = "INSERT INTO STUDENT(USER_NAME, FIRST_NAME, LAST_NAME, AGE) VALUES(?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(insertStudentSQL);
			pstmt.setString(1, student.getUserName());
			pstmt.setString(2, student.getFirstName());
			pstmt.setString(3, student.getLastName());
			pstmt.setString(4, student.getAge());
			pstmt.executeUpdate();
			Logger.debug("The student information is successfully saved");
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	/**
	 * This method will get the student information from "STUDENT" table based on the user name.
	 * @param userName to get the student details
	 * @throws SQLException 
	 */
	public static Student getStudent(String userName) throws SQLException {
		Connection con = null;
		Student student = null;
		try {
			con = DB.getConnection();
			Logger.debug("Getting the  student information with user name" + userName);
			String selectStudentSQL = "SELECT * FROM STUDENT WHERE USER_NAME='"+userName+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectStudentSQL);
			if (rs != null) {
				student = new Student();
				student.setUserName(rs.getString("USER_NAME"));
				student.setFirstName(rs.getString("FIRST_NAME"));
				student.setLastName(rs.getString("LAST_NAME"));
				student.setAge(rs.getString("AGE"));
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
		Logger.debug("Got the  student information");
		return student;
	}
}
