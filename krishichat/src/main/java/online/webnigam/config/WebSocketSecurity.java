package online.webnigam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurity extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//	@Override
//	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//
////		messages.simpDestMatchers("/connect/**").authenticated().anyMessage().authenticated();
//	}

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}

}
