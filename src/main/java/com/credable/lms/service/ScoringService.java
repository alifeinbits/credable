package com.credable.lms.service;

import com.credable.lms.model.ClientRegistration;
import com.credable.lms.repository.ClientRegistrationRepository;
import com.credable.lms.request.ClientRegistrationRequest;
import com.credable.lms.response.RegisterClientResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ScoringService {
    private final RestTemplate restTemplate;
    private final ScoringEngineService scoringEngineService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Value("${scoring.engine.url}")
    private String baseUrl;

    public ScoringService(RestTemplate restTemplate, ScoringEngineService scoringEngineService, ClientRegistrationRepository clientRegistrationRepository) {
        this.restTemplate = restTemplate;
        this.scoringEngineService = scoringEngineService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    public RegisterClientResponse registerClient(String endpointUrl, String name, String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String registerUrl = baseUrl + "/client/createClient";
        ClientRegistrationRequest request = new ClientRegistrationRequest(endpointUrl, name, username, password);
        HttpEntity<ClientRegistrationRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RegisterClientResponse> response =
                restTemplate.exchange(registerUrl, HttpMethod.POST, entity, RegisterClientResponse.class);

//        return response.getBody();
        RegisterClientResponse registerClientResponse = mockRegisterClient(endpointUrl, name, username, password);
        //Save
        ClientRegistration clientRegistration = new ClientRegistration();
        clientRegistration.setId(registerClientResponse.getId());
        clientRegistration.setToken(registerClientResponse.getToken());
        clientRegistration.setUrl(registerClientResponse.getUrl());
        clientRegistration.setName(registerClientResponse.getName());
        clientRegistration.setUsername(registerClientResponse.getUsername());
        clientRegistration.setPassword(registerClientResponse.getPassword());
        clientRegistration.setCreatedAt(LocalDateTime.now());
        clientRegistration.setUpdatedAt(LocalDateTime.now());
        clientRegistrationRepository.save(clientRegistration);

        return registerClientResponse;

    }

    //Mock this response

    public RegisterClientResponse mockRegisterClient(String endpointUrl, String name, String username, String password) {
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

