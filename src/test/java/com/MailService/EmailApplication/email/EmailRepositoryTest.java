package com.MailService.EmailApplication.email;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
class EmailRepositoryTest {

    @Autowired
    private EmailRepository testRepo;

    @AfterEach
    void tearDown(){
        testRepo.deleteAll();
    }
    @Test
    void itShouldCheckIfEmailExistInDatabaseByEmailAddress() {
        //given
        EmailAddress emailAddress = new EmailAddress("kordulas18@onet.eu");
        testRepo.save(emailAddress);
        //when
        Optional<EmailAddress> address = testRepo.findByEmailAddress(emailAddress.getEmailAddress());
        //then
        assertThat(address).isNotEmpty();
    }
    @Test
    void itShouldBeEmptyIfEmailNotExistInDatabase() {
        //given
        EmailAddress emailAddress = new EmailAddress("kordulas18@onet.eu");
        testRepo.save(emailAddress);
        //when
        Optional<EmailAddress> address = testRepo.findByEmailAddress("kubak@gmail.com");
        //then
        assertThat(address).isEmpty();
    }
}