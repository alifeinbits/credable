package com.credable.lms.service;

import com.credable.lms.exception.InvalidScoreResponseException;
import com.credable.lms.response.ScoreResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ScoringEngineService {
    private final RestTemplate restTemplate;

    @Value("${scoring.engine.url}")
    private String scoringEngineUrl;

    @Value("${scoring.engine.client-token}")
    private String clientToken;

    public ScoringEngineService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String initiateQueryScore(String customerNumber) {
        String url = scoringEngineUrl + "/scoring/initiateQueryScore/" + customerNumber;
        HttpHeaders headers = new HttpHeaders();
        headers.set("client-token",clientToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    @Retryable(
            value = {HttpServerErrorException.class, InvalidScoreResponseException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    public ScoreResponse fetchScore(String token) {
        String url = scoringEngineUrl + "/scoring/queryScore/" + token;
        HttpHeaders headers = new HttpHeaders();
        headers.set("client-token", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ScoreResponse> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, ScoreResponse.class);
        } catch (HttpServerErrorException e) {
            throw new HttpServerErrorException(e.getStatusCode(), "Server error, retrying...");
        }

        // Check if response is valid
        if (response.getStatusCode() != HttpStatus.OK || !isValidResponse(response.getBody())) {
            throw new InvalidScoreResponseException("Invalid score response, retrying...");
        }

        return response.getBody();
    }

    private boolean isValidResponse(ScoreResponse response) {
        return response != null &&
                response.getId() != null &&
                response.getScore() != null &&
                response.getLimitAmount() != null;
    }
}

