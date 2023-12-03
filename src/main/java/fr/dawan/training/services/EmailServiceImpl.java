package fr.dawan.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmailServiceImpl implements IEmailService {
	
	@Autowired
	private JavaMailSender emailSender;

	@Value("${spring.mail.properties.mail.from}")
	private String from;
	
	@Value("${frontapp.url}")
	private String frontAppUrl;
	
	@Override
	public void sendHtmlEmail(String subject, String text, String recipients) throws MessagingException {
		MimeMessage m = emailSender.createMimeMessage();
		m.setFrom(from);
		m.setRecipients(Message.RecipientType.TO, recipients);
		m.setSubject(subject);
		m.setText(text,"UTF-8", "html");
		emailSender.send(m);
	}

	@Override
	public void sendEmailForResetPwd(String email, String jwtToken) throws MessagingException {
		String resetLink = "<a href=\"" + frontAppUrl + "/#/fr/reset-password?token=" + jwtToken + "\">Réinitialiser</a>";

		String subject = "Réinitialisation du mot de passe";
		
		String text = "Bonjour,"
				+ "<br /> Ce message vous a été envoyé car vous avez oublié votre mot de passe.<br />"
				+ "Pour le réinitialiser, cliquez sur ce lien : " + resetLink;
				
		sendHtmlEmail(subject, text, email);
		
	}

}
