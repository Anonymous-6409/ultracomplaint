package com.ultrasoft.ultracomplaint.responsebody;

public class APIResponseBody {

    public int status_code;

    private String message;

    private Object data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public APIResponseBody(String message, Object data) {
        this.status_code = 1;
        this.message = message;
        this.data = data;
    }

    public APIResponseBody(String message) {
        this.status_code = 0;
        this.message = message;
    }
}
