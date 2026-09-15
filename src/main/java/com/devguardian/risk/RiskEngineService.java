package com.devguardian.risk;

import org.springframework.stereotype.Service;

@Service
public class RiskEngineService {
    private final RiskScoreRepository riskRepo;
    private final RiskScoreCalculator calculator;

    public RiskEngineService(RiskScoreRepository riskRepo, RiskScoreCalculator calculator) {
        this.riskRepo = riskRepo;
        this.calculator = calculator;
    }

    public RiskScore getRiskScore(Long repoId) { return riskRepo.findById(repoId).orElse(new RiskScore()); }
    public RiskScore recalculate(Long repoId) {
        RiskScore score = new RiskScore();
        score.setRepoId(repoId);
        score.setScore(calculator.calculate(repoId));
        return riskRepo.save(score);
    }
}