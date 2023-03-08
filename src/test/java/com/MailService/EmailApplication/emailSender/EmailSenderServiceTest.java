package com.MailService.EmailApplication.emailSender;

import com.MailService.EmailApplication.email.EmailAddress;
import com.MailService.EmailApplication.email.EmailRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class EmailSenderServiceTest {

    @Autowired
    private EmailRepository emailRepository;
    private AutoCloseable autoCloseable;
    private EmailSenderService senderServiceTest;
    private JavaMailSender javaMailSender;


    @BeforeEach
    void setUp(){
        javaMailSender = new JavaMailSenderImpl();
        autoCloseable = MockitoAnnotations.openMocks(this);
        senderServiceTest = new EmailSenderService(emailRepository,javaMailSender);
    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
        emailRepository.deleteAll();
    }
    String emailAddress = "probne13@gmail.com";
    String emailAddress1 = "probne12@gmail.com";
    String exampleText = "Email body test";
    String exampleSubject = "Email subject test";
    @Test
    void shouldReturnExceptionWhenRepositoryIsEmpty() {
        //when
        Exception exception = assertThrows(Exception.class,()
                -> {senderServiceTest.sendEmailsToAllUsers(exampleSubject,exampleText);});
        String expectedMessage = "There's no Emails in Database";
        String actualMessage = exception.getMessage();
        //then
        assertEquals(expectedMessage,actualMessage);
    }
    @Test
    void shouldReturnEstablishedStringWhenEmailIsSend() {
        //when
        emailRepository.save(new EmailAddress(emailAddress));
        String actualMessage = senderServiceTest.sendEmailsToAllUsers(exampleSubject,exampleText);
        String expectedMessage = "Mail send complete";
        //then
        assertEquals(expectedMessage,actualMessage);
    }
}