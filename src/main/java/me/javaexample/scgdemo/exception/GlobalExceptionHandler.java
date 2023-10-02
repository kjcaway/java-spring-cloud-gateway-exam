package me.javaexample.scgdemo.exception;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.result.view.ViewResolver;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;


@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {


    public GlobalExceptionHandler(ObjectProvider<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer, ErrorAttributes errorAttributes, ApplicationContext applicationContext) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
        super.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()));
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        try {
            final Map<String, Object> errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());
            int httpStatus = (int) errorPropertiesMap.get("status");
            return ServerResponse.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(errorPropertiesMap));

        } catch (Exception e) {
            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue(e.getMessage()));
        }
    }
}