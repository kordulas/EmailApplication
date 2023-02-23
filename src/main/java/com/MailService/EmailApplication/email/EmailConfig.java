package com.MailService.EmailApplication.email;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/** Simple class to provide basic data to test API
 */
@Configuration
public class EmailConfig {

    @Bean
    CommandLineRunner commandLineRunner(EmailRepository repository){
        return args -> {
            EmailAddress mail1 = new EmailAddress(
                    "probne13@gmail.com");
            EmailAddress mail2 = new EmailAddress(
                    "probne12@gmail.com");
            repository.saveAll(List.of(mail1,mail2));
        };
    }
}
