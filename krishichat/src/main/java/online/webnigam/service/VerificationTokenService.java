package online.webnigam.service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.webnigam.dao.VerificationTokenDAO;
import online.webnigam.entity.User;
import online.webnigam.entity.VerificationToken;

@Service
public class VerificationTokenService {

	@Autowired
	VerificationTokenDAO tokenDAO;

	@Autowired
	MailService mailService;

	@Autowired
	ServletContext servletContext;

	public void sendTo(User user, String purpose, String contextPath) {

		String random = UUID.randomUUID().toString();
		String message = "";
		String subject = "";

		if (purpose == VerificationToken.EMAILVERIFICATION) {
			message = "click to verify your email... " + servletContext.getInitParameter("domainName")
					+ "/confirmRegistration?token=" + random;
			subject = "Email Verification from CHATTER PAD APPLICATION";
		}

		if (purpose == VerificationToken.FORGOTPASSWORD) {
			message = "click to forgot your password... " + servletContext.getInitParameter("domainName")
					+ "/showForgotPassword?token=" + random;

			subject = "Forgot Password from CHATTER PAD APPLICATION";
		}

		// Add record to database
		VerificationToken token = new VerificationToken();
		token.setPurpose(purpose);
		token.setExpiryDate(new Date());
		token.setToken(random);
		token.setUser(user);
		add(token);

		// send email with token
		mailService.sendMail(subject, user.getEmail(), message);
	}

	public void add(VerificationToken token) {
		tokenDAO.addVerificationToken(token);
	}

	public VerificationToken findByTokenAndPurpose(String token, String purpose) {
		return tokenDAO.findByTokenAndPurpose(token, purpose);
	}

	public User isValid(String token, String purpose) {
		VerificationToken verificationToken = findByTokenAndPurpose(token, purpose);
		if (verificationToken == null) {
			return null;
		}
		Date expiryDate = verificationToken.getExpiryDate();
		String minuteToExpireToken = "";
		if (purpose.equals(VerificationToken.EMAILVERIFICATION))
			minuteToExpireToken = servletContext.getInitParameter("minuteToExpireTokenForEmailVerification");
		else {
			minuteToExpireToken = servletContext.getInitParameter("minuteToExpireTokenForForgotPassword");
		}
		Date currentTime = new Date();

		Calendar expiryCalender = Calendar.getInstance();
		expiryCalender.setTime(expiryDate);

		expiryCalender.add(Calendar.MINUTE, Integer.parseInt(minuteToExpireToken));

		expiryDate = expiryCalender.getTime();
		if (expiryDate.before(currentTime)) {
			return null;
		}
		return verificationToken.getUser();
	}

	public void deleteToken(String token) {
		tokenDAO.deleteToken(token);

	}

}
