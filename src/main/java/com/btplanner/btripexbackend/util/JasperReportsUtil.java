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
        InputStream inputStream = this.getClass().getResourceAsStream("/reports/EventExpenseReport.jrxml");

        EventBean eventBean = new EventBean();
        eventBean.setEvents(events);

        ArrayList<EventBean> databeanlist = new ArrayList<EventBean>();
        databeanlist.add(eventBean);

        JRBeanCollectionDataSource EVENTS_DATA_SOURCE = new JRBeanCollectionDataSource(databeanlist);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("IMAGE_DIR", "reports/images/");
        parameters.put("STYLE_DIR", "reports/");


        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, EVENTS_DATA_SOURCE);

        ByteArrayOutputStream finalReport = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, finalReport);
        return finalReport.toByteArray();
    }
}
