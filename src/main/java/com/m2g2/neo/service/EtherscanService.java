package com.m2g2.neo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class EtherscanService {

    @Value("${etherscan-api.url}")
    private String url;

    private final RestTemplate restTemplate;

    public EtherscanService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getGweiPrice() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                JsonNode node = new ObjectMapper().readTree(responseEntity.getBody());
                return BigDecimal.valueOf(Double.parseDouble(node.get("result").get("ProposeGasPrice").asText()));
            } catch (JsonProcessingException e) {
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }
}
