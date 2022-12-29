package com.example.demo.seed.socket.config;

import com.example.demo.device.repository.UserDeviceRepository;
import com.example.demo.security.jwt.JwtUtil;
import com.example.demo.security.jwt.Properties;
import com.example.demo.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.*;

import java.util.Objects;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDeviceRepository userDeviceRepository;
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

                if(StompCommand.CONNECT.equals(accessor.getCommand()))
                {
                    String authHeader = accessor.getFirstNativeHeader(Properties.HEADER_STRING);
                    String MAC = accessor.getFirstNativeHeader("MAC");

                    if(authHeader == null && MAC == null)
                    {
                        throw new IllegalArgumentException("권한이 없음");
                    }
                    else
                    {
                        if(authHeader == null)
                        {
                            if(!userDeviceRepository.existsByUserDeviceMAC(MAC))
                            {
                                throw new IllegalArgumentException("등록되지 않은 MAC");
                            }
                        }
                        else if(MAC == null)
                        {
                            if(!refreshTokenRepository.existsByRefreshToken(Objects.requireNonNull(accessor.getFirstNativeHeader(Properties.HEADER_STRING))))
                            {
                                throw new IllegalArgumentException("토큰이 없음");
                            }
                            if(JwtUtil.isTokenExpired(authHeader))
                            {
                                refreshTokenRepository.deleteByRefreshToken(authHeader);
                                throw new IllegalArgumentException("토큰이 만료됨");
                            }
                        }
                    }
                }
                return message;
            }
        });
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/device").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/client/socket").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
