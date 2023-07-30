package com.dpd.DpdJavaApp.model.response;

public class PersonResponse {

    private String message;
    private boolean exceptionThrown;

    public PersonResponse(String message, boolean exceptionThrown) {
        this.message = message;
        this.exceptionThrown = exceptionThrown;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getExceptionThrown() {
        return exceptionThrown;
    }

    public void setExceptionThrown(boolean exceptionThrown) {
        this.exceptionThrown = exceptionThrown;
    }

}
