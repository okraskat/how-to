package io.okraskat.requestcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/random")
class RandomController {

    private final RandomGenerator randomGenerator;

    @Autowired
    RandomController(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @GetMapping
    int getRandomNumberSum() {
        int first = randomGenerator.getRandomNumber();
        int second = randomGenerator.getRandomNumber();
        return first + second;
    }

}
