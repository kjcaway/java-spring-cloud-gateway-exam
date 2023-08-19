package me.javaexample.scgdemo.route;

import me.javaexample.scgdemo.entity.RouteInfoRepository;
import me.javaexample.scgdemo.filter.AuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicRouteConfig {
    @Bean
    public RouteLocator dynamicRouteLocator(RouteLocatorBuilder builder, RouteInfoRepository routeInfoRepository, AuthFilter authFilter) {
        return new DynamicRouteLocator(builder, routeInfoRepository, authFilter);
    }
}
