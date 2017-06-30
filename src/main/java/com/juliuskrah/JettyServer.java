package com.juliuskrah;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Slf4j
public class JettyServer {
    public static final String BASE_URI = "http://0.0.0.0/";

    public static void main(String... cmd) throws Exception {
        // The port that we should run on can be set into an environment variable
        // Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        URI baseUri = UriBuilder.fromUri(BASE_URI).port(Integer.valueOf(webPort)).build();
        ResourceConfig config = new ResourceConfig().packages("com.juliuskrah.service");
        JettyHttpContainerFactory.createServer(baseUri, config);
        log.info("Jersey app started and accessed from {}://{}:{}/", baseUri.getScheme(), baseUri.getHost(), baseUri.getPort());
    }
}