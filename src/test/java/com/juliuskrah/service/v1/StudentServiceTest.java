/*
* Copyright 2017, Julius Krah
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.juliuskrah.service.v1;

import com.juliuskrah.entity.Student;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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

    @Test
    public void testFindAllStudents() {
        // @formatter:off
        List<Student> students = target("v1/students")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<Student>>() {
                });

        assertThat(students, is(not(empty())));
        // @formatter:on
    }

    @Test
    public void testFindAllStudentWithRestAssured() {
        get("/v1/students/")
                .then().body("id", hasItems("1235", "1240"))
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
