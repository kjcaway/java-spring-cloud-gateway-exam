package me.javaexample.scgdemo.filter;

import io.jsonwebtoken.Claims;
import me.javaexample.scgdemo.exception.GlobalException;
import me.javaexample.scgdemo.jwt.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final JwtUtils jwtUtils;

    public AuthFilter(JwtUtils jwtUtils) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
    }

    public static class Config {
        public String baseMessage;
        public boolean preLogger;
        public boolean postLogger;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Request Header 에 token 이 존재하지 않을 때
            if(!request.getHeaders().containsKey("token")){
                //return handleUnAuthorized(exchange); // 401 Error
                throw new GlobalException(HttpStatus.BAD_REQUEST, "token is empty");
            }

            // Request Header 에서 token 문자열 받아오기
            List<String> token = request.getHeaders().get("token");
            String tokenString = Objects.requireNonNull(token).get(0);

            // 토큰 검증
            Claims claims = jwtUtils.parseJwtToken(tokenString);
            if(claims.isEmpty()) {
                //return handleUnAuthorized(exchange); // 토큰이 일치하지 않을 때
                throw new GlobalException(HttpStatus.BAD_REQUEST, "token claims is empty");
            }

            return chain.filter(exchange); // 토큰이 일치할 때
        });
    }

//    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {
//        ServerHttpResponse response = exchange.getResponse();
//
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        return response.setComplete();
//    }
}