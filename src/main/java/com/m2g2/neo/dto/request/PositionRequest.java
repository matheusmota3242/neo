package com.m2g2.neo.dto.request;

import java.math.BigDecimal;

public record PositionRequest(Long id,
                              String ticker,
                              BigDecimal amount,
                              BigDecimal averagePrice,
                              BigDecimal targetPrice,
                              BigDecimal valueUSD) {}