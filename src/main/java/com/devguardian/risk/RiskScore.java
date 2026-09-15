package com.devguardian.risk;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "risk_scores")
@Data
public class RiskScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long repoId;
    private int score;
}