package fr.dawan.training.services;

import jakarta.mail.MessagingException;

public interface IEmailService {

	/**
	 * Send an email
	 * @param subject
	 * @param text
	 * @param recipients
	 * @throws MessagingException
	 */
	void sendHtmlEmail(String subject, String text, String recipients) throws MessagingException;
	
	/**
	 * Send token to reset the user's password
	 * @param email					a valid username
	 * @param jwtToken				token to reset password
	 * @throws MessagingException	if send mail error
	 */
	void sendEmailForResetPwd(String email, String jwtToken) throws MessagingException;
}
