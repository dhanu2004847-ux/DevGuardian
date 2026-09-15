package com.devguardian.scanner;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TruffleHogRunner implements ScannerAdapter {
    @Override
    public List<Finding> scan(Long repoId, ScanResult scanResult) {
        // TODO: shell out to the trufflehog CLI, parse --json output into Findings.
        return List.of();
    }
}
