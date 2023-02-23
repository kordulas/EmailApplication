package com.MailService.EmailApplication.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailAddress,Long> {

    Optional<EmailAddress> findByEmailAddress(String email);
}
