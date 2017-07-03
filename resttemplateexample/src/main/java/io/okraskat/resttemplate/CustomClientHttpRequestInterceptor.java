package io.okraskat.resttemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomClientHttpRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        LOGGER.debug("Request to: {}", request.getURI());
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        LOGGER.debug("Response code: {}", response.getRawStatusCode());
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        LOGGER.trace("=============================request begin================================================");
        LOGGER.trace("URI         : {}", request.getURI());
        LOGGER.trace("Method      : {}", request.getMethod());
        LOGGER.trace("Headers     : {}", request.getHeaders());
        LOGGER.trace("Request body: {}", new String(body, "UTF-8"));
        LOGGER.trace("=============================request end================================================");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder responseBodyBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
        String line = bufferedReader.readLine();
        while (line != null) {
            responseBodyBuilder.append(line).append(System.lineSeparator());
            line = bufferedReader.readLine();
        }
        LOGGER.trace("============================response begin==========================================");
        LOGGER.trace("Status code : {}", response.getStatusCode());
        LOGGER.trace("Status text : {}", response.getStatusText());
        LOGGER.trace("Headers     :");
        response.getHeaders().forEach((headerName, headerValue) -> LOGGER.trace("Header name : {}, header value: {}", headerName, headerValue));
        LOGGER.trace("Response body: {}", responseBodyBuilder.toString());
        LOGGER.trace("=======================response end=================================================");
    }

}
