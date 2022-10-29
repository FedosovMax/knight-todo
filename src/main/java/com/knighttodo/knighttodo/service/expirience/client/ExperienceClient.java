package com.knighttodo.knighttodo.service.expirience.client;

import com.knighttodo.knighttodo.exception.ExperienceClientException;
import com.knighttodo.knighttodo.service.expirience.request.ExperienceRequest;
import com.knighttodo.knighttodo.service.expirience.response.ExperienceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

import static com.knighttodo.knighttodo.Constants.API_BASE_URL;
import static com.knighttodo.knighttodo.Constants.BASE_EXPERIENCE_URL;

@RequiredArgsConstructor
@Slf4j
@Component
public class ExperienceClient {

    private final RestTemplate restTemplate;

    @Value("${baseUrl.experience}")
    private String experienceUrl;

    public ExperienceResponse calculateExperience(ExperienceRequest experienceRequest) {
        try {
            ResponseEntity<ExperienceResponse> responseEntity = restTemplate
                    .postForEntity(experienceUrl + API_BASE_URL + BASE_EXPERIENCE_URL, experienceRequest,
                            ExperienceResponse.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            log.error(e.getResponseBodyAsString(), e);
            throw new ExperienceClientException(e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsByteArray(),
                    Charset.defaultCharset());
        }
    }
}
