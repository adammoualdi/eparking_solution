package com.parkingapp.server.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private HttpStatus statusCode;
    private String errorContent;
    private List<ErrorDTO> messages;

    public ErrorResponse(HttpStatus statusCode, String errorContent, ErrorDTO messages) {
        this.statusCode = statusCode;
        this.errorContent = errorContent;
        this.messages = new ArrayList<>();
        this.messages.add(messages);
    }

    public ErrorResponse(HttpStatus statusCode, String errorContent, List<ErrorDTO> messages) {
        this.statusCode = statusCode;
        this.errorContent = errorContent;
        this.messages = messages;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }

    public List<ErrorDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<ErrorDTO> messages) {
        this.messages = messages;
    }
}
