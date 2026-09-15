package com.devguardian.scanner;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TrivyRunner implements ScannerAdapter {
    @Override
    public List<Finding> scan(Long repoId, ScanResult scanResult) {
        // TODO: shell out to the trivy CLI, parse --format json output into Findings.
        return List.of();
    }
}
