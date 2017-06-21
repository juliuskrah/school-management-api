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
package com.juliuskrah;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

@ApplicationPath("/")
public class ResourcePath extends Application {

	public static void main(String... cmd) throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		
		tomcat.start();
		tomcat.getServer().await();
	}
}
