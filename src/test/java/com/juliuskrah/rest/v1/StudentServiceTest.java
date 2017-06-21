package com.juliuskrah.rest.v1;

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
	public void testGet() {
		// @formatter:off
		final String id = "abeiku";
		final Student student = target("v1")
				.path("{id}")
				.path("student")
				.resolveTemplate("id", id)
				.request(MediaType.APPLICATION_JSON)
				.get(Student.class);
		assertEquals("abeiku", student.getId());
		// @formatter:on
	}

	@Test
	public void testGetWithRestAssured() {
		// @formatter:off
		get("/v1/abeiku/student")
		.then().body("id", equalTo("abeiku"))
		.statusCode(200);
		// @formatter:on
	}

	@Test
	public void testPost() {
		// @formatter:off
		Student student = new Student() {
			{
				setId("awesome");
			}
		};

		Response response = target("v1")
				.path("add")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.json(student));

		assertThat(response.getStatus(), is(201));
		assertThat(response.getLocation(), 
				equalTo(URI.create(String
						.format("http://localhost:9998/v1/%s/student", student.getId()))));
		// @formatter:on
	}

	@Test
	public void testPostWithRestAssured() {
		// @formatter:off
		Map<String, String> student = new HashMap<>();
		student.put("id", "awesome");

		given()
		.contentType(MediaType.APPLICATION_JSON)
		.body(student)
		.when().post("/v1/add")
		.then()
		.body("id", equalTo("awesome"))
		.statusCode(201);
		// @formatter:on
	}

}
