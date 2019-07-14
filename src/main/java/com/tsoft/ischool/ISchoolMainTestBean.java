
package com.tsoft.ischool;

import com.tsoft.ischool.service.reports.JournalCaisseReport;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;

@SpringBootApplication
class ISchoolMainTestBean {
    public static void main(String[] args) {
        ApplicationContext ctx = new SpringApplicationBuilder(ISchoolMainTestBean.class).run();
        System.out.println("\niSchool started");
        JournalCaisseReport journalCaisseReport = ctx.getBean(JournalCaisseReport.class);

        try{
          //  journalCaisseReport.process("05/07/2019");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        ((ConfigurableApplicationContext) ctx).close();
        System.out.println("\niSchool Ended");
    }

}

