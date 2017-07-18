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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * This represents the service resource for a Student entity
 * The resources should be name appropriately
 * <p>
 * {@link http://www.restapitutorial.com/lessons/restfulresourcenaming.html}
 */
@Slf4j
@Path("/v1/students/") // <- required
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StudentService {
    private static List<Student> students;

    static {
        students = new LinkedList<>();
        students.add(new Student("1231"));
        students.add(new Student("1232"));
        students.add(new Student("1233"));
        students.add(new Student("1234"));
        students.add(new Student("1235"));
        students.add(new Student("1236"));
        students.add(new Student("1237"));
        students.add(new Student("1238"));
        students.add(new Student("1239"));
        students.add(new Student("1240"));
    }

    /**
     * This returns a student resource with the specified {@code id}. Returns Status code {@code 404}
     * if no resource is found with the specified {@code id}
     *
     * @param id Identifier for the Student Resource
     * @return Response this wraps the Student entity
     * {@link http://docs.oracle.com/javaee/7/tutorial/jaxrs002.htm}
     */
    @GET
    @Path("{id}")
    public Response findStudent(@PathParam("id") String id) {
        log.info("Getting student {}", id);
        Student student = new Student();
        student.setId(id);
        Response.ResponseBuilder builder = null;

        int index = Collections.binarySearch(students, student, Comparator.comparing(Student::getId));

        if (index >= 0) {
            builder = Response.ok()
                    .entity(students.get(index));
        } else
            throw new WebApplicationException(Response.Status.NOT_FOUND);

        return builder.build();
    }

    /**
     * Creates a new Student resource with the supplied payload.
     * Sends back a Response code of {@code 201} when successfully created. A {@code Location} header is also
     * returned to the client with the location of the newly created resource
     *
     * @param student
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createStudent(Student student) {
        // A common application pattern is to use POST to create a resource and
        // return a 201 response with a location header
        // whose value is the URI to the newly created resource
        log.info("Student: {}", student);
        int index = Collections.binarySearch(students, student, Comparator.comparing(Student::getId));
        if (index < 0) {
            students.add(student);
            Response.ResponseBuilder builder = null;
            builder = Response.status(Response.Status.CREATED)
                    .location(URI.create(String.format("/v1/students/%s", student.getId())))
                    // or .header(HttpHeaders.LOCATION, String.format(BASE_URL,
                    // student.getId()))
                    .entity(student);

            return builder.build();
        } else
            throw new WebApplicationException(Response.Status.CONFLICT);
    }

    /**
     * This returns a collection of all student resources found
     *
     * @return {@code List<Student>} A collection of all Student resource
     */
    @GET
    public List<Student> findAllStudents() {
        // TODO change to remote address. This logs all addresses making a service call
        MDC.put("username", "Julius");
        log.debug("Returning list of Students...");
        MDC.remove("username");
        return students;
    }
}
