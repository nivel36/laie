package es.nivel36.laie.ejb.core.mail;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;

@Stateless
public class MailService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	private @Resource(lookup = "java:jboss/mail/Default") Session mailSession;
	private @Inject MailTemplateService mailTemplateService;

	private MimeMessage buildMessage(final Mail mail) throws MessagingException {
		final MimeMessage message = new MimeMessage(this.mailSession);
		message.setRecipients(Message.RecipientType.TO, mail.getTo());
		message.setSubject(mail.getSubject());
		message.setFrom(mail.getFrom());
		message.setSentDate(new Date());
		message.setContent(mail.getMessage(), "text/html");
		return message;
	}

	@Asynchronous
	@Lock(LockType.READ)
	public void sendMail(final Mail mail) throws MessagingException {
		Objects.requireNonNull(mail);
		final MimeMessage message = this.buildMessage(mail);
		Transport.send(message);
		logger.info("Email {} sended to {} ", mail.getSubject(), mail.getTo());
		logger.trace("From: {}", mail.getFrom());
		logger.trace("Message: {}", mail.getMessage());
	}

	public void sendMail(final String to, final String from, final String templateName,
			final Map<String, String> parameters) throws MessagingException {
		Objects.requireNonNull(to);
		Objects.requireNonNull(from);
		Objects.requireNonNull(templateName);
		final MailTemplate mailTemplate = this.mailTemplateService.findMailTemplate(templateName);
		final Mail mail = new Mail(to, from, mailTemplate.getSubject(), mailTemplate.buildMessage(parameters));
		this.sendMail(mail);
	}

	public void setMailSession(final Session mailSession) {
		this.mailSession = Objects.requireNonNull(mailSession);
	}

	public void setMailTemplateService(MailTemplateService mailTemplateService) {
		this.mailTemplateService = Objects.requireNonNull(mailTemplateService);
	}
}
