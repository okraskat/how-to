package io.okraskat.throttling;

public class RequestLimitReached extends RuntimeException {
    public RequestLimitReached(String userId, EndpointMethod endpointMethod) {
        super(String.format("User: %s, reached calls limit for method: %s", userId, endpointMethod.toString()));
    }
}
