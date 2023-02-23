package com.MailService.EmailApplication.emailSender;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/emailSender")
@RequiredArgsConstructor
@Slf4j
public class EmailSenderController {

    private final EmailSenderService senderService;

    @ApiOperation(value = "Send email to user", notes = "send email to all emails in database")
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    public String sendEmail(@RequestParam String subject, @RequestParam String body){
        senderService.sendEmailsToAllUsers(subject,body);
        return "Message sent";
    }
}
