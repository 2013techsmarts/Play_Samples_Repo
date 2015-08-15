package controllers;

import java.sql.SQLException;

import model.Student;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This controller has been created to understand the controller and actions 
 * @author Siva Janapati
 *
 */
public class StudentController extends Controller {


	/**
	 * This method will return the student who name is matches
	 * @param name - to fetch the student info
	 * @return - the JSON representation of matched student
	 */
	public Result getStudent(String userName) {
		Logger.debug("The student name is " + userName);
		Student student = null;
		try {
			student = Student.getStudent(userName);
			return ok(Json.toJson(student));
		} catch (SQLException sqlException) {
			Logger.error("Some thing went wrong while getting the student information with user name" + userName, sqlException);
			return status(500, "Some went wrong while getting the student information with user name" + userName);
		}
	}

	/**
	 * This method saves the student.
	 * @return - The available students in map with JSON representation
	 */
	public Result saveStudent() {
		JsonNode json = request().body().asJson();
		Logger.debug("json" + json.toString());
		try {
			Student.saveStudent(json);
			return ok("Student information is successfully saved");
		} catch (SQLException sqlException) {
			Logger.error("Some thing went wrong while saving the student information", sqlException);
			return status(500, "Some went wrong while saving the student information");
		}
	}
}
