package com.btplanner.btripexbackend.datamodel.accountmodel;

public class ExpenseReport {
    private String encodedPDFReport;

    public ExpenseReport(String encodedPDFReport) {
        this.encodedPDFReport = encodedPDFReport;
    }

    public String getEncodedPDFReport() {
        return encodedPDFReport;
    }

    public void setEncodedPDFReport(String encodedPDFReport) {
        this.encodedPDFReport = encodedPDFReport;
    }
}
