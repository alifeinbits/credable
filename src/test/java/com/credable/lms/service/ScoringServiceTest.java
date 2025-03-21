package com.credable.lms.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.credable.lms.exception.InvalidScoreResponseException;
import com.credable.lms.response.ScoreResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class ScoringServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ScoringEngineService scoringService;

    @Test
    void testFetchScoreRetriesOnInvalidResponse() {
        String token = "test-token";
        String url = "https://scoringtest.credable.io/api/v1/scoring/queryScore/" + token;
        HttpEntity<String> entity = new HttpEntity<>(null);

        ScoreResponse invalidResponse = new ScoreResponse(); // Missing required fields
        ResponseEntity<ScoreResponse> responseEntity = ResponseEntity.ok(invalidResponse);

        when(restTemplate.exchange(url, HttpMethod.GET, entity, ScoreResponse.class))
                .thenReturn(responseEntity);

        assertThrows(InvalidScoreResponseException.class, () -> scoringService.fetchScore(token));

        verify(restTemplate, times(3)).exchange(url, HttpMethod.GET, entity, ScoreResponse.class);
    }
}

