//package com.tsoft.ischool.service.reports;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//
//import com.tsoft.ischool.domain.Caisse;
//import com.tsoft.service.process.EmptyReportProcess;
//import com.tsoft.utils.FileUtils;
//import com.tsoft.utils.annotations.Select;
//import java.sql.Timestamp;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import javax.persistence.JoinColumn;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.validation.constraints.NotNull;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author tchipi
// */
//@Service
//public class EtatCaissePeriode  {
//
//   
//
//    @Override
//    public Object run(HttpSession session, HttpServletRequest request) throws Exception {
//        String reportfile = "";
//        //remplissage des parametres du report
//        Map params = new HashMap();
//        reportfile = "classpath:com/tsoft/appli/highschool/finances/service/EtatCaissePeriode.jasper";
//        Instant instant_debut = Instant.ofEpochMilli(getDate_debut().getTime());
//        LocalDate datedebut=LocalDateTime.ofInstant(instant_debut, ZoneId.of("UTC")).toLocalDate();
//        params.put("date_debut", Timestamp.valueOf(LocalDateTime.of(datedebut, LocalTime.MIN)));
//        Instant instant_fin = Instant.ofEpochMilli(getDate_fin().getTime());
//        LocalDate datefin=LocalDateTime.ofInstant(instant_fin, ZoneId.of("UTC")).toLocalDate();
//        params.put("date_fin", Timestamp.valueOf(LocalDateTime.of(datefin, LocalTime.MAX)));
//        params.put("code_caisse", getCaisse().getCode());
//
//        String destfile = FileUtils.getReportFile("EtatCaisse"
//                + System.currentTimeMillis() + ".pdf");
//        //fill report
//        JasperPrint jp = JasperFillManager.fillReport(
//                resourceLoader.getResource(reportfile).getInputStream() //file jasper
//                , params, //params report
//                dataSource.getConnection());  //datasource
//
//        JasperExportManager.exportReportToPdfFile(jp, destfile);
//
//        return download(destfile);
//    }
//
//   
//
//}
