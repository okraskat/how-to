package io.okraskat.throttling;

import java.util.Optional;

public class UserIdProvider {
    /*
    replace with your logic to provide user id
     */
    public static Optional<String> getCurrentUserId() {
        return Optional.of("test@domain.com");
    }
}
