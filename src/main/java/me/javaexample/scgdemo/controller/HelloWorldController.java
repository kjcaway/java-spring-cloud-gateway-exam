package me.javaexample.scgdemo.controller;

import me.javaexample.scgdemo.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/helloworld")
public class HelloWorldController {
    @GetMapping("/error404")
    public Mono<?> notFound() {
        throw new GlobalException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/error409")
    public Mono<?> conflict() {
        throw new GlobalException(HttpStatus.CONFLICT, "conflict");
    }

    @GetMapping("/error503")
    public Mono<?> gatewayTimeout() {
        throw new GlobalException(HttpStatus.GATEWAY_TIMEOUT, "gatewayTimeout", new Exception("new exception"));
    }
}
