package com.devguardian.scanner;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScanOrchestratorService {
    private final List<ScannerAdapter> scanners;
    private final ScanResultRepository scanResultRepository;
    private final FindingRepository findingRepository;

    public ScanOrchestratorService(List<ScannerAdapter> scanners, ScanResultRepository scanResultRepository, FindingRepository findingRepository) {
        this.scanners = scanners;
        this.scanResultRepository = scanResultRepository;
        this.findingRepository = findingRepository;
    }

    public ScanResult runScans(Long repoId) {
        ScanResult result = new ScanResult();
        result.setRepoId(repoId);
        scanResultRepository.save(result);

        for (ScannerAdapter scanner : scanners) {
            List<Finding> findings = scanner.scan(repoId, result);
            findings.forEach(f -> f.setScanResult(result));
            findingRepository.saveAll(findings);
        }
        return result;
    }

    public ScanResult getScanResult(Long scanId) {
        return scanResultRepository.findById(scanId).orElseThrow();
    }

    public List<Finding> getFindings(Long scanId) {
        return findingRepository.findByScanResultId(scanId);
    }

    public List<ScanResult> getScanHistory(Long repoId) {
        return scanResultRepository.findByRepoId(repoId);
    }
}
