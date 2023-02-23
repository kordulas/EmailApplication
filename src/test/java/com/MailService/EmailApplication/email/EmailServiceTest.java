package com.MailService.EmailApplication.email;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class EmailServiceTest {

    @Autowired
    private EmailRepository emailRepository;
    private AutoCloseable autoCloseable;
    private EmailService serviceTest;
    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        serviceTest = new EmailService(emailRepository);
    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
        emailRepository.deleteAll();
    }

    String emailAddress = "probne13@gmail.com";
    String emailAddress1 = "probne12@gmail.com";
    String emailAddress2 = "probne12gmail.com";
    String emailAddress3 = "probne12gmailcom";


    //addNewEmailAddress()
    @Test
    void testSaveEmailToRepositoryAndEmailIsCorrect() {
        //when
        emailRepository.save(new EmailAddress(emailAddress));
        //then
        assertNotNull(emailRepository);
    }
    @Test
    void shouldReturnExceptionWhenEmailAddressIsIncorrect() {
        //when
        Exception exception = assertThrows(Exception.class, ()
                -> {
            serviceTest.addNewEmailAddress(emailAddress3);
        });
        String expectedMessage = "You use not allowed email address format";
        String actualMessage = exception.getMessage();
        //then
        assertEquals(expectedMessage,actualMessage);
    }
    @Test
    void shouldReturnEmailTakenExceptionWhenEmailAddressIsTaken() {
        //when
        //serviceTest.addNewEmailAddress(emailAddress);
        emailRepository.save(new EmailAddress(emailAddress));
        Exception exception = assertThrows(Exception.class,()
                -> {serviceTest.addNewEmailAddress(emailAddress);});
        String expectedMessage = "Email already exist in database";
        String actualMessage = exception.getMessage();
        //then
        assertEquals(expectedMessage,actualMessage);
    }
    //getAllEmails()
    @Test
    void shouldReturnEmailListWhenRepositoryIsNotEmpty() {
        //when
        emailRepository.save(new EmailAddress(emailAddress));
        //then
        assertNotNull(emailRepository);
    }
    @Test
    void shouldReturnExceptionWhenRepositoryIsEmpty() {
        //when
        Exception exception = assertThrows(Exception.class,()
                -> {serviceTest.getAllEmails();});
        String expectedMessage = "There's no Emails in Database";
        String actualMessage = exception.getMessage();
        //then
        assertEquals(expectedMessage,actualMessage);
    }
//    getEmailById
    @Test
    void shouldReturnEmailByIdIfItsPresent() {
        //when
        emailRepository.save(new EmailAddress(emailAddress));
        //then
        assertNotNull(emailRepository.findById(1L));
    }
    @Test
    void shouldReturnExceptionIfEmailByIdIsNotPresent() {
        //when
        Exception exception = assertThrows(Exception.class,()
        -> {serviceTest.getEmailById(5L);});
        String expectedMessage = "Email with 5 was not found";
        String actualMessage = exception.getMessage();
        //then
        assertEquals(expectedMessage,actualMessage);
    }
    //deleteEmailAddressFromDatabase
    @Test
    void shouldDeleteEmailAddressFromDataBase() {
        //when
        emailRepository.save(new EmailAddress(emailAddress1));
        serviceTest.deleteEmailAddressFromDatabase(emailAddress1);
        //then
        assertTrue(emailRepository.findByEmailAddress(emailAddress1).isEmpty());
    }
    @Test
    void shouldReturnExceptionIfDeleteEmailAddressIsIncorrect() {
        //when
        emailRepository.save(new EmailAddress(emailAddress1));
        Exception exception = assertThrows(Exception.class,()
                -> {serviceTest.deleteEmailAddressFromDatabase("probne20gmail.com");});
        String expectedMessage = "You use not allowed email address format";
        String actualMessage = exception.getMessage();
        //then
        assertEquals(expectedMessage,actualMessage);
    }
    @Test
    void shouldReturnExceptionIfDeleteEmailAddressIsNotInDatabase() {
        //when
        emailRepository.save(new EmailAddress(emailAddress1));
        Exception exception = assertThrows(Exception.class,()
                -> {serviceTest.deleteEmailAddressFromDatabase("probne@20gmail.com");});
        String expectedMessage = "There's no such Email in Database";
        String actualMessage = exception.getMessage();
        //then
        assertEquals(expectedMessage,actualMessage);
    }
    //updateEmail
    @Test
    void shouldUpdateEmailWhenEmailIsCorrectAndIsNotTakenYet() {
        //when
        emailRepository.save(new EmailAddress(emailAddress));
        serviceTest.updateEmail(emailAddress,"probne20@gmail.com");
        Optional<EmailAddress> byId = emailRepository.findById(1L);
        EmailAddress address = byId.get();

        //then
        assertEquals("probne20@gmail.com", address.getEmailAddress());
    }
    @Test
    void shouldReturnWrongAddressFormatExceptionIfNewEmailAddressIsIncorrect() {
        //when
        emailRepository.save(new EmailAddress(emailAddress));
        Exception exception = assertThrows(Exception.class,()
                -> {serviceTest.updateEmail(emailAddress,"probne20gmail.com");});
        String expectedMessage = "You use not allowed email address format";
        String actualMessage = exception.getMessage();
        EmailAddress referenceById = emailRepository.getReferenceById(1L);
        //then
        assertEquals(expectedMessage,actualMessage);
    }
    @Test
    void shouldReturnWrongAddressExceptionIfOldEmailAddressIsIncorrect() {
        //when
        emailRepository.save(new EmailAddress(emailAddress));
        Exception exception = assertThrows(Exception.class,()
                -> {serviceTest.updateEmail("probne13gmail.com","probne@20gmail.com");});
        String expectedMessage = "You use not allowed email address format";
        String actualMessage = exception.getMessage();
        EmailAddress referenceById = emailRepository.getReferenceById(1L);
        //then
        assertEquals(expectedMessage,actualMessage);
    }
    @Test
    void shouldReturnEmailNotFoundExceptionIfOldEmailAddressWasNotFound() {
        //when
        emailRepository.save(new EmailAddress(emailAddress));
        Exception exception = assertThrows(Exception.class,()
                -> {serviceTest.updateEmail("probne14@gmail.com","probne@20gmail.com");});
        String expectedMessage = "There's no such Email in Database";
        String actualMessage = exception.getMessage();
        EmailAddress referenceById = emailRepository.getReferenceById(1L);
        //then
        assertEquals(expectedMessage,actualMessage);
    }
    @Test
    void shouldReturnEmailExceptionIfOldEmailAddressAndNewEmailAddressAreSame() {
        //when
        emailRepository.save(new EmailAddress(emailAddress));
        Exception exception = assertThrows(Exception.class,()
                -> {serviceTest.updateEmail(emailAddress,emailAddress);});
        String expectedMessage = "You use not allowed email address format or " +
                " email address is already used";
        String actualMessage = exception.getMessage();
        EmailAddress referenceById = emailRepository.getReferenceById(1L);
        //then
        assertEquals(expectedMessage,actualMessage);
    }
}