package me.javaexample.scgdemo.controller;

import me.javaexample.scgdemo.jwt.JwtUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtils jwtUtils;

    public AuthController(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/token")
    public Mono<?> token() {
        String token = jwtUtils.makeJwtToken();

        return Mono.just(Map.of(
                "status", "OK",
                "data", token)
        );
    }
}
