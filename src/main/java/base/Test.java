package base;

public class Test {
    private String testClassName;
    private String testMethodName;
    private String errorMessage;
    private Boolean hasFails;


    public Test(String testClassName, String testMethodName, String message, Boolean hasFails) {
        setTestClassName(testClassName);
        setTestMethodName(testMethodName);
        setErrorMessage(message);
        setHasFails(hasFails);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public Boolean getHasFails() {
        return hasFails;
    }

    public void setHasFails(Boolean hasFails) {
        this.hasFails = hasFails;
    }
}
