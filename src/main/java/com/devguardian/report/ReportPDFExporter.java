package com.devguardian.report;
import org.springframework.stereotype.Component;
@Component
public class ReportPDFExporter {
    public byte[] export(Report report) { return new byte[0]; }
}