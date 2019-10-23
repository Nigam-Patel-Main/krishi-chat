package online.webnigam.service;

import org.springframework.social.google.api.plus.Person;

public interface AuthenticationService {

	String genrateUrl();

	String getAuthenticationToken(String code);

	Person fetchUserFromGoogle(String authToken);

}
