package me.javaexample.scgdemo.controller;

import me.javaexample.scgdemo.jwt.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtils jwtUtils;

    public AuthController(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/token")
    public Mono<ResponseEntity<?>> token() {
        String token = jwtUtils.makeJwtToken();

        return Mono.just(ResponseEntity.ok(token));
    }
}
