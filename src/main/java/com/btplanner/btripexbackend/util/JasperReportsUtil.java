package com.btplanner.btripexbackend.util;

import com.btplanner.btripexbackend.datamodel.accountmodel.Event;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.OutputStreamExporterOutput;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JasperReportsUtil {

    public byte[] generateEventReport(List<Event> events) throws IOException, JRException, URISyntaxException {
        InputStream inputStream = new FileInputStream("src/main/resources/reports/EventExpenseReport.jrxml");
        OutputStreamExporterOutput outputStream = null;

        EventBean eventBean = new EventBean();
        eventBean.setEvents(events);

        ArrayList<EventBean> databeanlist = new ArrayList<EventBean>();
        databeanlist.add(eventBean);

        JRBeanCollectionDataSource EVENTS_DATA_SOURCE = new JRBeanCollectionDataSource(databeanlist);
        Map<String, Object> parameters = new HashMap<String, Object>();
        //parameters.put("EVENTS_DATA_SOURCE", EVENTS_DATA_SOURCE);
        parameters.put("IMAGE_DIR", "reports/images/");
        parameters.put("STYLE_DIR", "reports/");


        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, EVENTS_DATA_SOURCE);
        //JasperExportManager.exportReportToPdfFile(jasperPrint, "tmp/event_expense_report.pdf");

        ByteArrayOutputStream finalReport = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, finalReport);
        return finalReport.toByteArray();

/*        OutputStream out = new FileOutputStream("tmp/event_expense_report.pdf");
        out.write(pdf);
        out.close();*/


/*        JRPdfExporter pdfExporter = new JRPdfExporter();
        pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput("src/main/resources/reports/event_expense_report.pdf"));
        //outputStream = pdfExporter.getExporterOutput();
        pdfExporter.exportReport();*/
    }
}
