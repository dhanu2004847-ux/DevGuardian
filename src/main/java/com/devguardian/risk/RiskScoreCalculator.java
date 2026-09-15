package com.devguardian.risk;
import org.springframework.stereotype.Component;
@Component
public class RiskScoreCalculator {
    public int calculate(Long repoId) { return 85; }
}