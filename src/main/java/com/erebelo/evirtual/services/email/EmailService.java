package com.erebelo.evirtual.services.email;

import org.springframework.mail.SimpleMailMessage;

import com.erebelo.evirtual.domain.CustomerOrder;

public interface EmailService {

	void sendOrderConfirmationEmail(CustomerOrder obj);

	void sendEmail(SimpleMailMessage msg);
}
