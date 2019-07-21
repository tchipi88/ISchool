package com.tsoft.ischool;



import com.tsoft.ischool.config.ApplicationProperties;
import com.tsoft.ischool.config.DefaultProfileUtil;
import com.tsoft.ischool.domain.enumeration.ModePaiement;
import com.tsoft.ischool.service.reports.JournalCaisseReport;
import com.tsoft.ischool.service.reports.LaunchPaymentReport;
import com.tsoft.ischool.service.reports.PaymentPeriodReport;
import io.github.jhipster.config.JHipsterConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@ComponentScan
@EnableScheduling
@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class})
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
public class ISchool {

    private static final Logger log = LoggerFactory.getLogger(ISchool.class);

    private final Environment env;

    public ISchool(Environment env) {
        this.env = env;
    }

    /**
     * Initializes TKBR_.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="http://jhipster.github.io/profiles/">http://jhipster.github.io/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not" +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(ISchool.class);
        DefaultProfileUtil.addDefaultProfile(app);
        ApplicationContext ctx = app.run(args);
        Environment env = ctx.getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
//        LaunchPaymentReport launchPaymentReport = ctx.getBean(LaunchPaymentReport.class);
//        JournalCaisseReport journalCaisseReport = ctx.getBean(JournalCaisseReport.class);
//        PaymentPeriodReport paymentPeriodReport = ctx.getBean(PaymentPeriodReport.class);
//        try{
//            journalCaisseReport.process("12/07/2019");
//            paymentPeriodReport.process("10/07/2019", "13/07/2019", ModePaiement.ESPECES, null);
//            launchPaymentReport.generateDailyCashReport();
//            launchPaymentReport.sendMail();
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }

        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}\n\t" +
                "External: \t{}://{}:{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            env.getProperty("server.port"),
            protocol,
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"),
            env.getActiveProfiles());
    }
}
