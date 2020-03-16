package com.knighttodo.knighttodo.gateway.experience.client;

import static com.knighttodo.knighttodo.Constants.buildCalculateExperienceBaseUrl;

import com.knighttodo.knighttodo.exception.ExperienceClientException;
import com.knighttodo.knighttodo.gateway.experience.request.TodoRequest;
import java.nio.charset.Charset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
public class ExperienceClient {

    private final RestTemplate restTemplate;

    @Value("${baseUrl.experience}")
    private String experienceUrl;


    public void calculateTodo(TodoRequest todoRequest) {
        try {
            restTemplate
                .exchange(buildCalculateExperienceBaseUrl(), HttpMethod.POST, new HttpEntity<>(todoRequest),  Void.class);
        } catch (HttpClientErrorException e) {
            log.error(e.getResponseBodyAsString(), e);
            throw new ExperienceClientException(e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsByteArray(),
                Charset.defaultCharset());
        }
    }
}
