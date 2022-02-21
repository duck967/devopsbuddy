package com.devopsbuddy.config;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.devopsbuddy.backend.service.EmailService;
import com.devopsbuddy.backend.service.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailSender;

import io.awspring.cloud.ses.SimpleEmailServiceMailSender;


/**
 * Created by tedonema on 21/03/2016.
 * 
 * https://reflectoring.io/spring-cloud-aws-ses/
 */
@Configuration
@Profile("prod")
@PropertySource("file:///${user.home}/projects/devopsbuddy/.devopsbuddy/application-prod.properties")
public class ProductionConfig {

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {

      return AmazonSimpleEmailServiceClientBuilder.standard()
          .withCredentials(new ProfileCredentialsProvider("default"))
          .withRegion(Regions.US_EAST_2)
          .build();
    }

    @Bean
    public MailSender mailSender(
                AmazonSimpleEmailService amazonSimpleEmailService) {
      return new SimpleEmailServiceMailSender(amazonSimpleEmailService);
    }
}