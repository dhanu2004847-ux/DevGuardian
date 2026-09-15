package com.devguardian.scanner;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "scan_results")
@Data
public class ScanResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long repoId;
}