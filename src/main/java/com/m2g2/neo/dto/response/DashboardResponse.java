package com.m2g2.neo.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(BigDecimal gwei, List<PositionResult> positions) {}
