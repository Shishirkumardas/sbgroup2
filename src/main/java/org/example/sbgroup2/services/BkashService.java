package org.example.sbgroup2.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class BkashService {

    private final String baseUrl = "https://tokenized.sandbox.bka.sh/v1.2.0-beta"; // sandbox URL

    private final String username = "YOUR_USERNAME";
    private final String password = "YOUR_PASSWORD";
    private final String appKey = "YOUR_APP_KEY";
    private final String appSecret = "YOUR_APP_SECRET";

    private String token;

    public String generateToken() {
        // You can use RestTemplate or WebClient
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = Map.of(
                "app_key", appKey,
                "app_secret", appSecret
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                baseUrl + "/checkout/token/grant",
                entity,
                Map.class
        );

        token = (String) response.getBody().get("id_token");
        return token;
    }
}
