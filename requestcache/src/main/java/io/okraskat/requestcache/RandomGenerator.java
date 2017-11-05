package io.okraskat.requestcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
class RandomGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomGenerator.class);
    private static final Random RANDOM = new Random();

    @RequestCache
    int getRandomNumber() {
        LOGGER.info("Generating random number ...");
        return RANDOM.nextInt(100) + 1;
    }
}
