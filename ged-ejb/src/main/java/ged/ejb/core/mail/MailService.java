package ged.ejb.core.mail;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class MailService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Resource(lookup = "java:jboss/mail/Default")
	private Session mailSession;

	@Inject
	private MailTemplateService mailTemplateService;

	public void sendMail(final String to, final String from, final String templateName, Map<String, String> parameters)
			throws MessagingException {
		Objects.requireNonNull(to);
		Objects.requireNonNull(from);
		Objects.requireNonNull(templateName);
		final MailTemplate mailTemplate = mailTemplateService.findMailTemplate(templateName);
		final Mail mail = new Mail(to, from, mailTemplate.getSubject(), mailTemplate.buildMessage(parameters));
		sendMail(mail);
	}

	private MimeMessage buildMessage(final Mail mail) throws MessagingException {
		final MimeMessage message = new MimeMessage(mailSession);
		message.setRecipients(Message.RecipientType.TO, mail.getTo());
		message.setSubject(mail.getSubject());
		message.setFrom(mail.getFrom());
		message.setSentDate(new Date());
		message.setContent(mail.getMessage(), MediaType.TEXT_HTML);
		return message;
	}

	@Asynchronous
	@Lock(LockType.READ)
	public void sendMail(final Mail mail) throws MessagingException {
		Objects.requireNonNull(mail);
		final MimeMessage message = buildMessage(mail);
		Transport.send(message);
		logger.info("Email {} sended to {} ", mail.getSubject(), mail.getTo());
		logger.trace("From: {}", mail.getFrom());
		logger.trace("Message: {}", mail.getMessage());
	}

	public void setMailSession(Session mailSession) {
		this.mailSession = mailSession;
	}
}
