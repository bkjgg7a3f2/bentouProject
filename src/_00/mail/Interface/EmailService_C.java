package _00.mail.Interface;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import _01_model._01_customer.Customer;

public interface EmailService_C {
	public void validationLink(Customer c) throws AddressException, MessagingException, IOException;

	public void failedRegistration(String acc, String email) throws AddressException, MessagingException, IOException;

	public void passwordResetLink(Customer c) throws AddressException, MessagingException, IOException;
}
