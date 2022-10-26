package com.example.demo.seed.socket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;
    private final TestSocketHandler testSocketHandler;
    private final ClientSocketHandler clientSocketHandler;
    private final test t;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //registry.addHandler(webSocketHandler,"/test").setAllowedOriginPatterns("*");
        registry.addHandler(testSocketHandler,"/testtest").setAllowedOriginPatterns("*"); //main
        registry.addHandler(clientSocketHandler, "/client/socket").setAllowedOriginPatterns("*").withSockJS().setInterceptors(t);
    }

}
