package com.knighttodo.knighttodo.exception;

import java.nio.charset.Charset;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ExperienceClientException extends HttpClientErrorException {

    public ExperienceClientException(HttpStatus statusCode, String statusText, byte[] body, Charset responseCharset) {
        super(statusCode, statusText, body, responseCharset);
    }
}
