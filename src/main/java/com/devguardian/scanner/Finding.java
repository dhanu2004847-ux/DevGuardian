package com.devguardian.scanner;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "findings")
@Data
public class Finding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String severity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scan_result_id", nullable = false)
    private ScanResult scanResult;
}
