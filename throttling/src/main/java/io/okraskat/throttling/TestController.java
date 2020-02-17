package io.okraskat.throttling;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController("/test")
public class TestController {
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @GetMapping
    @Throttling(timeFrameInSeconds = 60, calls = 2)
    public String exampleEndpoint() {
        System.out.println("Call number: " + atomicInteger.incrementAndGet());
        return UUID.randomUUID().toString();
    }
}
