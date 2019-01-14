package helpers;

import java.util.concurrent.ThreadLocalRandom;

public class TestDataHelpers {

    public static int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
