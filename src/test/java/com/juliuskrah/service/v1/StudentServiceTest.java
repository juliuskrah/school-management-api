package com.juliuskrah.service.v1;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.junit.Test;

import com.juliuskrah.entity.Student;

import io.restassured.RestAssured;

public class StudentServiceTest extends JerseyTest {
	@BeforeClass
	public static void setup() {
		RestAssured.port = 9998;
	}

	@Override
	protected Application configure() {
		return new ResourceConfig(StudentService.class);
	}

	@Test
	public void testFindStudent() {
		// @formatter:off
		final String id = "1232";
		final Student student = target("v1/students")
				.path("{id}")
				.resolveTemplate("id", id)
				.request(MediaType.APPLICATION_JSON)
				.get(Student.class);
		assertEquals("1232", student.getId());
		// @formatter:on
	}

	@Test
	public void testFindStudentWithRestAssured() {
		// @formatter:off
		get("/v1/students/1232")
		.then().body("id", equalTo("1232"))
		.statusCode(200);
		// @formatter:on
	}

	@Test
	public void testCreateStudent() {
		// @formatter:off
		Student student = new Student() {
			{
				setId("1241");
			}
		};

		Response response = target("v1/students")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.json(student));

		assertThat(response.getStatus(), is(201));
		assertThat(response.getLocation(), 
				equalTo(URI.create(String
						.format("http://localhost:9998/v1/students/%s", student.getId()))));
		// @formatter:on
	}

	@Test
	public void testCreateStudentWithRestAssured() {
		// @formatter:off
		Map<String, String> student = new HashMap<>();
		student.put("id", "1241");

		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(student)
		.when().post("/v1/students")
		.then()
		.body("id", equalTo("1241"))
		.statusCode(201);
		// @formatter:on
	}

	// TODO testFindAllStudents
	// TODO testFindAllStudentWithRestAssured
}
