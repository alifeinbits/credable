package com.credable.lms.service;

import com.credable.lms.request.ClientRegistrationRequest;
import com.credable.lms.response.RegisterClientResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ScoringService {
    private final RestTemplate restTemplate;
    private final ScoringEngineService scoringEngineService;

    @Value("${scoring.engine.register.url}")
    private String registerUrl;

    public ScoringService(RestTemplate restTemplate, ScoringEngineService scoringEngineService) {
        this.restTemplate = restTemplate;
        this.scoringEngineService = scoringEngineService;
    }

    /*public RegisterClientResponse registerClient(String endpointUrl, String name, String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ClientRegistrationRequest request = new ClientRegistrationRequest(endpointUrl, name, username, password);
        HttpEntity<ClientRegistrationRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RegisterClientResponse> response =
                restTemplate.exchange(registerUrl, HttpMethod.POST, entity, RegisterClientResponse.class);

        return response.getBody();
    }*/

    //Mock this response

    public RegisterClientResponse registerClient(String endpointUrl, String name, String username, String password) {
        return new RegisterClientResponse(
                ThreadLocalRandom.current().nextLong(100000, 999999),
                endpointUrl,
                name,
                username,
                password,
                UUID.randomUUID().toString()
        );
    }

}

