package manager;

import base.Test;

public class TestManager extends ThreadLocal {
    private static ThreadLocal<Test> test = new ThreadLocal<>();

    public static Test getTest() {
        return test.get();
    }

    public static void setTest(Test testEntity) {
        test.set(testEntity);
    }
}
