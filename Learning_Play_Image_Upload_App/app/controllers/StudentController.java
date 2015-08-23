package controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import model.Student;
import play.Logger;
import play.Play;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

/**
 * This controller has been created to understand the controller and actions 
 * @author Siva Janapati
 *
 */
public class StudentController extends Controller {

	/**
	 * To imitate the CURD features, created the map with students
	 */
	public static Map<String, Student> students = new HashMap<String, Student>();

	/**
	 * Filling the data to the students map
	 */
	static {
		Student st1 = new Student();
		st1.setUserName("USN1");
		st1.setFirstName("FSN1");
		st1.setLastName("LSN1");
		st1.setAge("10");
		Student st2 = new Student();
		st2.setUserName("USN2");
		st2.setFirstName("FSN2");
		st2.setLastName("LSN2");
		st2.setAge("20");
		students.put("USN1", st1);
		students.put("USN2", st2);

	}

	/**
	 * This method will return the student who name is matches
	 * @param name - to fetch the student info
	 * @return - the JSON representation of matched student
	 */
	public Result getStudent(String userName) {
		Logger.debug("The student name is " + Play.application().configuration().getString("mode"));
		Logger.debug("The student name is " + userName);
		Student student = students.get(userName);
		if (student != null) {
			return ok(Json.toJson(student));
		} else {
			return notFound("Student not found");
		}
	}

	/**
	 * This method will return the student who name is matches. Here the action receives the "name" as query parameter.
	 * @param name - to fetch the student info
	 * @return - the JSON representation of matched student
	 */
	public Result getStudentByQueryParam() {
		Map<String, String[]> queryParams = request().queryString();
		if (queryParams == null || queryParams.isEmpty()) {
			Logger.error("The required query parameters are not found");
			return status(500, "please send the required query parameters");
		}
		Student student = students.get(queryParams.get("UN")[0]);
		if (student != null) {
			return ok(Json.toJson(student));
		} else {
			return notFound("Student not found");
		}
	}

	/**
	 * This method saves the student.
	 * @return - The available students in map with JSON representation
	 */
	public Result saveStudent() {
		JsonNode json = Json.parse(request().body().asMultipartFormData().asFormUrlEncoded().get("json")[0]);
		MultipartFormData formData = request().body().asMultipartFormData();
		File logo = null;
		if (formData != null) {
			FilePart logoFilePart = formData.getFile("student_pic");
			if (logoFilePart != null) {
				logo = logoFilePart.getFile();
				System.out.println("File Uploaded Name ::"+logoFilePart.getFilename());
			}
		}
		Logger.debug("json" + json.toString());
		Student st = new Student();
		st.setUserName(json.findPath("userName").textValue());
		st.setFirstName(json.findPath("firstName").textValue());
		st.setLastName(json.findPath("lastName").textValue());
		st.setAge(json.findPath("age").textValue());
		students.put(json.findPath("userName").textValue(), st);   
		Logger.debug("The aviable students size is " + students.size());
		return ok(Json.toJson(students.values()));
	}

	/**
	 * This method deletes the student from the students map
	 * @param name - to delete the student
	 * @return - The available students in map with JSON representation.
	 */
	public Result deleteStudent(String userName) {
		Logger.debug("The student to delete" + userName);
		students.remove(userName);
		return ok(Json.toJson(students.values()));
	}
}
