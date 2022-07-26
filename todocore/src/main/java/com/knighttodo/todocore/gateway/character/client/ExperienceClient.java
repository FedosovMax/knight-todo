package com.knighttodo.todocore.gateway.character.client;

//import com.knighttodo.character.rest.ExperienceResource;
//import com.knighttodo.todoapp.gateway.character.mapper.ExperienceMapper;
//import com.knighttodo.todoapp.gateway.character.request.ExperienceRequest;
//import com.knighttodo.todoapp.gateway.character.response.ExperienceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class ExperienceClient {

//    private final ExperienceResource experienceResource;
//    private ExperienceMapper experienceMapper;
//
//    public ExperienceResponse calculateExperience(ExperienceRequest experienceRequest) {
//        return experienceMapper.toExperienceResponse(experienceResource
//                .calculateTodo(experienceMapper.toExperienceRequestDto(experienceRequest)).getBody());
//    }
/*
    Microservices implementation:

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
 */
}
