package _00.mail.Interface;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import _01_model._02_supply.Supply;

public interface EmailService_S {
	public void validationLink(Supply s) throws AddressException, MessagingException, IOException;

	public void failedRegistration(String acc, String email) throws AddressException, MessagingException, IOException;

	public void passwordResetLink(Supply s) throws AddressException, MessagingException, IOException;
}
