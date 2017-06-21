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
package com.juliuskrah.rest.v1;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.juliuskrah.entity.Student;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/v1") // <- required
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class StudentService {
	/**
	 * 
	 * @param id
	 *            Identifier for the Student Resource
	 * @return Student
	 * @see http://docs.oracle.com/javaee/7/tutorial/jaxrs002.htm
	 */
	@GET
	@Path("{id}/student")
	public Student getStudent(@PathParam("id") String id) {
		log.info("Getting student...");
		Student student = new Student();
		student.setId(id);
		return student;
	}

	/**
	 *
	 * @param student
	 * @return
	 * @throws URISyntaxException
	 */
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createStudent(Student student) {
		// A common application pattern is to use POST to create a resource and
		// return a 201 response with a location header
		// whose value is the URI to the newly created resource
		log.info("Student: {}", student);
		Response.ResponseBuilder builder = null;
		builder = Response.status(Response.Status.CREATED)
				.location(URI.create(String.format("/v1/%s/student", student.getId())))
				// or .header(HttpHeaders.LOCATION, String.format(BASE_URL,
				// student.getId()))
				.entity(student);

		return builder.build();
	}
}
