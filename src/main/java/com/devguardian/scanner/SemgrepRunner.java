package com.devguardian.scanner;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SemgrepRunner implements ScannerAdapter {
    @Override
    public List<Finding> scan(Long repoId, ScanResult scanResult) {
        // TODO: shell out to the semgrep CLI (ProcessBuilder), parse its --json output,
        // and map each result into a Finding attached to scanResult.
        return List.of();
    }
}
