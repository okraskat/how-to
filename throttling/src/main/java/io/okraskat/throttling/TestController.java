package io.okraskat.throttling;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/test")
public class TestController {

    @GetMapping
    @Throttling(timeFrameInSeconds = 60, calls = 2)
    public String exampleEndpoint() {
        return UUID.randomUUID().toString();
    }
}
