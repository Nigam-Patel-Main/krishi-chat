package online.webnigam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private String id = "249232638334-i8el0of788svpba006ktojjpk5fcimf1.apps.googleusercontent.com";

	private String secret = "e-TI_STpgyVWSk8_mxUfCPFF";

	@Autowired
	private GoogleConnectionFactory getGoogleConnectionFactory() {
		return new GoogleConnectionFactory(id, secret);
	}

	@Override
	public String genrateUrl() {
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri("http://localhost:8080/krishichat/loginlogic");
		parameters.setScope("email profile");
		return getGoogleConnectionFactory().getOAuthOperations().buildAuthenticateUrl(parameters);
	}

	@Override
	public String getAuthenticationToken(String code) {
		return getGoogleConnectionFactory().getOAuthOperations()
				.exchangeForAccess(code, "http://localhost:8080/krishichat/loginlogic", null).getAccessToken();
	}

	@Override
	public Person fetchUserFromGoogle(String authToken) {
		Google profile = new GoogleTemplate(authToken);
		Person person = profile.plusOperations().getGoogleProfile();
		System.out.println(authToken + "-" + person.getAccountEmail() + "-" + person.getDisplayName() + ""
				+ person.getEmailAddresses());
		return person;
	}

}
