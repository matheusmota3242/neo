package com.m2g2.neo.service;

import com.m2g2.neo.dto.response.DashboardResponse;
import com.m2g2.neo.dto.response.PositionResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DashboardService {

    private final PositionService positionService;
    private final EtherscanService etherscanService;

    public DashboardService(PositionService positionService, EtherscanService etherscanService) {
        this.positionService = positionService;
        this.etherscanService = etherscanService;
    }

    public DashboardResponse getDashboard() {
        List<PositionResult> results = positionService.getAllPositionResults();
        BigDecimal gwei = etherscanService.getGweiPrice().setScale(2, RoundingMode.HALF_UP);
        return new DashboardResponse(gwei, results);
    }
}
