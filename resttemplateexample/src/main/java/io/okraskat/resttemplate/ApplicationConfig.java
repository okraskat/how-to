package io.okraskat.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate(CustomClientHttpRequestInterceptor customClientHttpRequestInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = Collections.singletonList(customClientHttpRequestInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

}
