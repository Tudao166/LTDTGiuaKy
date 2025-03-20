package com.example.testgiuaky2.model;

public class ApiResponse<T> {
    int code;
    String message;
    T result;

    public ApiResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ApiResponse() {
    }

    public ApiResponse(int code) {
        this.code = code;
    }

    public ApiResponse(int code, T result) {
        this.code = code;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
// Lê Trường Sơn 22110507

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
