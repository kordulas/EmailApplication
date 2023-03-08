package com.MailService.EmailApplication.email;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    //@GetMapping(path = "/getAllEmails")
    @ApiOperation(value = "Get all emails in database", notes = "provide list of emails storage in database")
    @RequestMapping(value = "/getAllEmails", method = RequestMethod.GET)
    public List<EmailAddress> getAllEmails(){
        return emailService.getAllEmails();
    }
    //@GetMapping(path = "/getEmailById")
    @ApiOperation(value = "Get email in database by Id", notes = "provide info of email storage in database by id")
    @RequestMapping(value = "/getEmailById",params = "id",method = RequestMethod.GET)
    public EmailAddress emailById(Long id){
        return emailService.getEmailById(id);
    }
    @ApiOperation(value = "Add new email to database", notes = "save new email in database")
    @PostMapping(path = "/addNewEmail")
    public void newEmailToDatabase(@RequestBody String email){
        emailService.addNewEmailAddress(email.replaceAll("\"",""));
    }
    @ApiOperation(value = "Delete email from database", notes = "delete email from database by email address")
    @DeleteMapping(path = "/deleteEmail")
    public void deleteEmailFromDatabase(@RequestBody String email){
        emailService.deleteEmailAddressFromDatabase(email.replaceAll("\"",""));
    }
    @ApiOperation(value = "Update your email address", notes = "update your email if You change it")
    @PutMapping(path = "{updateEmail}")
    public void updateYourEmailAddress(@PathVariable("updateEmail") String email,@RequestParam String newEmail){
        emailService.updateEmail(email.replaceAll("\"","")
                                ,newEmail.replaceAll("\"",""));
    }
}
