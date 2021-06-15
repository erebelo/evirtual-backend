package com.erebelo.evirtual.services.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.domain.CustomerOrder;

public interface EmailService {

	void sendOrderConfirmationEmail(CustomerOrder obj);

	void sendEmail(SimpleMailMessage msg);

	void sendOrderConfirmationHtmlEmail(CustomerOrder obj);

	void sendHtmlEmail(MimeMessage msg);

	void sendPasswordRecoveryEmail(Customer customer, String newPassword);

	void sendPasswordRecoveryHtmlEmail(Customer customer, String newPassword);
}
