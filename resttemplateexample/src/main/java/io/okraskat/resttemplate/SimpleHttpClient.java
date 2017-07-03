package io.okraskat.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class SimpleHttpClient {
    private final RestTemplate restTemplate;
    private final String serviceUrl;

    @Autowired
    public SimpleHttpClient(RestTemplate restTemplate, @Value("${service.url}") String serviceUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = serviceUrl;
    }

    @PostConstruct
    public void postForEntity() {
        ServiceExchangeData request = buildRequestData();
        String url = serviceUrl + "/posts";
        restTemplate.postForEntity(url, request, ServiceExchangeData.class);
    }

    private ServiceExchangeData buildRequestData() {
        ServiceExchangeData request = new ServiceExchangeData();
        request.setTitle("Title");
        request.setBody("Body");
        request.setUserId(222L);
        return request;
    }
}
