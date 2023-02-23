package com.MailService.EmailApplication.email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;


/**
 * Simple class to represent an email adress
 * also ready to showing it as database record.
 * */
@Entity
@Component
@Table(name = "emails")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmailId")
    @Nullable
    @ApiModelProperty("id is given automatically")
    private Long id;

    @Column(name = "EmailAddress")
    @ApiModelProperty("unique email address")
    private String emailAddress;

    public EmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
