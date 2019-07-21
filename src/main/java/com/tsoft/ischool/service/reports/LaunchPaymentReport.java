package com.tsoft.ischool.service.reports;

import com.tsoft.ischool.service.mail.EmailService;
import com.tsoft.ischool.service.mail.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LaunchPaymentReport {

    @Autowired
    private JournalCaisseReport journalCaisseReport;

    @Autowired
    private PaymentPeriodReport paymentPeriodReport;

    @Autowired
    private EmailService emailService;

    @Value("${ischool.report.email}")
    String receivers;


    @Scheduled(cron = "${ischool.report.time.daily}")
    public void generateDailyPaymentReport() throws Exception{
        LocalDate date = LocalDate.now();
        String filename = journalCaisseReport.generateReport(date);

        Mail mail = new Mail();
        mail.setTo(receivers);
        String st = "Daily payment of "+date.toString();
        mail.setSubject(st);
        mail.setContent(st+"\nPlease find attached");

        emailService.sendMessageAttachedFile(mail, filename);
    }

    @Scheduled(cron = "${ischool.report.time.period}")
    public void generatePaymentPeriodReport() throws Exception{
        LocalDate date = LocalDate.now();
        LocalDate startDate = date.plusDays(7);
        String filename = paymentPeriodReport.generateReport(startDate, date);

        Mail mail = new Mail();
        mail.setTo(receivers);
        String st = "Payment Period of "+startDate.toString() + " to "+ date ;
        mail.setSubject(st);
        mail.setContent(st+"\nPlease find attached");

        emailService.sendMessageAttachedFile(mail, filename);
    }

//    public void sendMail() throws Exception{
//        LocalDate date = LocalDate.now();
//        String filename = journalCaisseReport.generateReport(date);
//
//        Mail mail = new Mail();
//        mail.setTo(receivers);
//        String st = "Daily payment of "+date.toString();
//        mail.setSubject(st);
//        mail.setContent(st+"\nPlease find attached");
//
//        emailService.sendSimpleMessage(mail);
//    }

}
