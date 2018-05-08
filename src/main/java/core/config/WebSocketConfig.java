package core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.websocket.handler.OrderHander;
import com.websocket.interceptor.OrderInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new OrderHander(),"/order").addInterceptors(new OrderInterceptor()); //支持websocket 的访问链接
        registry.addHandler(new OrderHander(),"/sockjs/order").addInterceptors(new OrderInterceptor()).withSockJS(); //不支持websocket的访问链接

	}


}
