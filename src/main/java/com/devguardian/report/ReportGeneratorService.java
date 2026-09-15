package com.devguardian.report;
import org.springframework.stereotype.Service;
@Service
public class ReportGeneratorService {
    public Report generate(Long repoId) { return new Report(); }
}