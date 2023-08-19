package me.javaexample.scgdemo.route;

import me.javaexample.scgdemo.entity.RouteInfoRepository;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicRouteConfig {
    @Bean
    public RouteLocator dynamicRouteLocator(RouteLocatorBuilder builder, RouteInfoRepository routeInfoRepository) {
        return new DynamicRouteLocator(builder, routeInfoRepository);
    }
}
