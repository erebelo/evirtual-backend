package com.erebelo.evirtual.services.email;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.erebelo.evirtual.domain.Customer;
import com.erebelo.evirtual.domain.CustomerOrder;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * Order Confirmation
	 */
	// Simple message from order confirmation email
	@Override
	public void sendOrderConfirmationEmail(CustomerOrder obj) {
		SimpleMailMessage sm = prepareSimpleMessageFromCustomerOrder(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMessageFromCustomerOrder(CustomerOrder obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCustomer().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Order confirmed! Code: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

	// Html message from order confirmation email
	@Override
	public void sendOrderConfirmationHtmlEmail(CustomerOrder obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromCustomerOrder(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessageFromCustomerOrder(CustomerOrder obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCustomer().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Order confirmed! Code: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromCustomerOrderTemplate(obj), true);
		return mimeMessage;
	}

	protected String htmlFromCustomerOrderTemplate(CustomerOrder obj) {
		Context context = new Context();
		context.setVariable("order", obj);
		return templateEngine.process("email/orderConfirmation", context);
	}

	/*
	 * Recovery Password
	 */
	// Simple message from password recovery email
	@Override
	public void sendPasswordRecoveryEmail(Customer obj, String newPassword) {
		SimpleMailMessage sm = prepareSimpleMessageFromPasswordRecoveryEmail(obj, newPassword);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMessageFromPasswordRecoveryEmail(Customer obj, String newPassword) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Password recovery");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("New password: " + newPassword);
		return sm;
	}

	// Html message from password recovery email
	@Override
	public void sendPasswordRecoveryHtmlEmail(Customer obj, String newPassword) {
		try {
			MimeMessage mm = prepareMimeMessageFromPasswordRecoveryEmail(obj, newPassword);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendPasswordRecoveryEmail(obj, newPassword);
		}
	}

	protected MimeMessage prepareMimeMessageFromPasswordRecoveryEmail(Customer obj, String newPassword) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Password recovery");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromPasswordRecoveryTemplate(obj, newPassword), true);
		return mimeMessage;
	}

	protected String htmlFromPasswordRecoveryTemplate(Customer obj, String newPassword) {
		Context context = new Context();
		context.setVariable("customer", obj);
		context.setVariable("newPassword", newPassword);
		context.setVariable("name", obj.getName().split(" ")[0]);
		return templateEngine.process("email/passwordRecovery", context);
	}
}
