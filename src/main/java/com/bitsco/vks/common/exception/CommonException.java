package com.bitsco.vks.common.exception;

import com.bitsco.vks.common.response.Response;

public class CommonException extends RuntimeException {
    private Response response;
    private String message;

    public CommonException() {
    }

    @Deprecated
    public CommonException(String message) {
        super(message);
        this.message = message;
    }

    public CommonException(Response response, String message) {
        super(message);
        this.response = response;
        this.message = message == null ? response.getResponseMessage() : message;
    }

    public CommonException(Response response) {
        super(response.getResponseMessage());
        this.response = response;
        this.message = response.getResponseMessage();
    }

    public CommonException(String responseCode, String message) {
        super(message);
        this.response.setResponseCode(responseCode);
        this.response.setResponseMessage(message);
        this.message = message == null ? response.getResponseMessage() : message;
    }


    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"responseCode\":").append("\"").append(this.getResponse().getResponseCode()).append("\"").append(",");
        stringBuilder.append("\"responseMessage\":").append("\"").append(this.getMessage() == null ? this.getResponse().getResponseMessage() : this.getMessage()).append("\"");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
