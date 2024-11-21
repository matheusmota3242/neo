package com.m2g2.neo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2g2.neo.dto.response.PositionResult;
import com.m2g2.neo.model.PositionRecord;
import com.m2g2.neo.repository.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PositionService {

    @Value("${coinmarketcap-api.url}")
    private String url;

    @Value("${coinmarketcap-api.key}")
    private String key;

    private static final Logger logger = LoggerFactory.getLogger(PositionService.class);

    private final PositionRepository repository;

    private final RestTemplate restTemplate;

    public PositionService(PositionRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public PositionRecord save(PositionRecord positionRecord) {
        positionRecord.setCreatedAt(LocalDateTime.now());
        return repository.saveAndFlush(positionRecord);
    }
    public List<PositionResult> getAllPositionResults() {
        List<PositionRecord> positionRecords = repository.findAll();
        if (CollectionUtils.isEmpty(positionRecords)) {
            return new ArrayList<>();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", key);
        headers.set("Accept", "application/json");
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url.concat("?symbol="+ positionRecords.stream().map(PositionRecord::getTicker).collect(Collectors.joining(","))),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return positionRecords.stream().map(p -> {
                BigDecimal currentPrice = extractCurrentPriceFromTicker(responseEntity.getBody(), p.getTicker());
                return new PositionResult(
                        p.getId(),
                        p.getTicker(),
                        p.getAmount(),
                        p.getAveragePrice().setScale(2, RoundingMode.HALF_UP),
                        p.getTargetPrice().setScale(2, RoundingMode.HALF_UP),
                        currentPrice,
                        p.getAmount().multiply(currentPrice).setScale(2, RoundingMode.HALF_UP));
            }).toList();
        }
        throw new RuntimeException("Erro inesperado.");
    }

    private static BigDecimal extractCurrentPriceFromTicker(String body, String ticker) {
        JsonNode node;
        try {
            logger.info("Retorno do coinmarketcap: {}", body);
            node = new ObjectMapper().readTree(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return BigDecimal.valueOf(
                Double.parseDouble(node.get("data")
                        .get(ticker)
                        .get("quote")
                        .get("USD")
                        .get("price").asText())).setScale(2, RoundingMode.HALF_UP);
    }

}
