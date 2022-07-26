package com.knighttodo.todocore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

public class ExperienceClientException extends HttpClientErrorException {

    public ExperienceClientException(HttpStatus statusCode, String statusText, byte[] body, Charset responseCharset) {
        super(statusCode, statusText, body, responseCharset);
    }
}
