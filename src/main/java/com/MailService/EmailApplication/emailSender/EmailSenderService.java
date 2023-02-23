package com.MailService.EmailApplication.emailSender;

import com.MailService.EmailApplication.Exceptions.EmailNotFoundException;
import com.MailService.EmailApplication.email.EmailAddress;
import com.MailService.EmailApplication.email.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailSenderService {

    private final EmailRepository emailRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(EmailRepository emailRepository, JavaMailSender javaMailSender) {
        this.emailRepository = emailRepository;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailsToAllUsers(String subject, String body) {
        List<String> mailList = fillList(emailRepository);
        String[] emailList = mailList.toArray(new String[0]);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("probnedlaonwelo@gmail.com");
        message.setTo(emailList);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);
    }
    /**Simple method to fill our List
     * @param emailRepository
     * @return if empty throw exception, otherwise return list with emails in database.
     */
    private List<String> fillList(EmailRepository emailRepository) {
        List<String> emailsList = emailRepository.findAll().stream()
                .map(EmailAddress::getEmailAddress)
                .collect(Collectors.toList());
        if(emailsList.isEmpty())
            throw new EmailNotFoundException("There's no Emails in Database");
        return emailsList;
    }
}
