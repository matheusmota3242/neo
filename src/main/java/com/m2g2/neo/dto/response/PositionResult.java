package com.m2g2.neo.dto.response;

import java.math.BigDecimal;

public record PositionResult(Long id,
                             String ticker,
                             BigDecimal amount,
                             BigDecimal averagePrice,
                             BigDecimal targetPrice,
                             BigDecimal currentPrice,
                             BigDecimal valueUSD) {}
