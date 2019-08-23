package online.webnigam.listner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import online.webnigam.event.EmailConfirmationEvent;
import online.webnigam.service.VerificationTokenService;

@Component
public class EmailConfirmationListner {

	@Autowired
	VerificationTokenService tokenService;

	@EventListener
	@Async
	public void onApplicationEvent(EmailConfirmationEvent event) {
		tokenService.sendTo(event.getUser(), event.getPurpose(), event.getContextPath());
	}

}
