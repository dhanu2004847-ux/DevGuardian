package com.devguardian.report;

import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportGeneratorService generatorService;
    private final ReportPDFExporter pdfExporter;

    public ReportService(ReportRepository reportRepository, ReportGeneratorService generatorService, ReportPDFExporter pdfExporter) {
        this.reportRepository = reportRepository;
        this.generatorService = generatorService;
        this.pdfExporter = pdfExporter;
    }

    public Report getReport(Long repoId) { return reportRepository.findById(repoId).orElseThrow(); }
    public Report generateReport(Long repoId) {
        Report report = generatorService.generate(repoId);
        return reportRepository.save(report);
    }
    public byte[] exportPDF(Long repoId) { return pdfExporter.export(getReport(repoId)); }
}