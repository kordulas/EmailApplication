package com.MailService.EmailApplication.email;

import com.MailService.EmailApplication.Exceptions.EmailAlreadyTakenException;
import com.MailService.EmailApplication.Exceptions.EmailNotFoundException;
import com.MailService.EmailApplication.Exceptions.WrongEmailAddressFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    public List<EmailAddress> getAllEmails(){
        List<EmailAddress> allEmails = emailRepository.findAll();
        log.info("Method getAllEmails has been called");
        return validateList(allEmails);
    }
    public EmailAddress getEmailById(Long emailId){
        Optional<EmailAddress> optionalEmail = emailRepository.findById(emailId);
        if(optionalEmail.isEmpty()) {
            throw new EmailNotFoundException(emailId);
        }
        log.info("Method getEmailById has been called");
        return optionalEmail.get();
    }
    public void addNewEmailAddress(String email){
        if(!validateStringAsCorrectEmailAddress(email)){
            throw new WrongEmailAddressFormatException("You use not allowed email address format");
        }
        Optional<EmailAddress> emailByAddress = emailRepository.findByEmailAddress(email);
        if(emailByAddress.isPresent())
            throw new EmailAlreadyTakenException("Email already exist in database");
        emailRepository.save(new EmailAddress(email));
        log.info("New email added to database " + email);
    }

    public void deleteEmailAddressFromDatabase(String email){
        if(!validateStringAsCorrectEmailAddress(email)){
            throw new WrongEmailAddressFormatException("You use not allowed email address format");
        }
        Optional<EmailAddress> emailByAddress = emailRepository.findByEmailAddress(email.replaceAll("\"", ""));
        emailByAddress.ifPresentOrElse(emailRepository::delete,
                () -> {
                    throw new EmailNotFoundException("There's no such Email in Database");
                });
        log.info(String.format("Address : %s deleted from database", email));
    }
    @Transactional
    public void updateEmail(String email, String newEmail){
        if(!validateStringAsCorrectEmailAddress(email)){
            throw new WrongEmailAddressFormatException("You use not allowed email address format");
        }
        if(!validateStringAsCorrectEmailAddress(newEmail)){
            throw new WrongEmailAddressFormatException("You use not allowed email address format");
        }
        EmailAddress oldEmail = emailRepository.findByEmailAddress(email).orElseThrow(
                () -> new EmailNotFoundException("There's no such Email in Database"));
        if(!validateStringAsCorrectEmailAddress(oldEmail.getEmailAddress())
                || email.equals(newEmail)){
            throw new WrongEmailAddressFormatException("You use not allowed email address format or " +
                    " email address is already used");
        }
        log.info("Method updateEmail has been called");
        oldEmail.setEmailAddress(newEmail);
    }
    /** Simple method to check if our List is empty and will throw unwanted
     * NullPointerException. Accept List which is possible empty,
     * if it is empty throw exception, if is not empty return list.
     * @param allEmails
     * @return verified list with emails.
     */
    private List<EmailAddress> validateList(List<EmailAddress> allEmails) {
        if(allEmails.isEmpty())
            throw new EmailNotFoundException("There's no Emails in Database");
        return allEmails;
    }
    /** Method to check if our string that represent email address
     * has correct format.
     * @param email
     * @return true if string included email address is correct, otherwise false.
     */
    private boolean validateStringAsCorrectEmailAddress(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        return Pattern.compile(regex)
                .matcher(email)
                .matches();
    }
}
