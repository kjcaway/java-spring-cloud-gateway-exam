package me.javaexample.scgdemo.route;

import lombok.RequiredArgsConstructor;
import me.javaexample.scgdemo.entity.RouteInfo;
import me.javaexample.scgdemo.entity.RouteInfoRepository;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import reactor.core.publisher.Flux;


public class DynamicRouteLocator implements RouteLocator {

    private final RouteLocatorBuilder builder;
    private final RouteInfoRepository routeInfoRepository;

    public DynamicRouteLocator(RouteLocatorBuilder builder, RouteInfoRepository routeInfoRepository) {
        this.builder = builder;
        this.routeInfoRepository = routeInfoRepository;
    }

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routesBuilder = builder.routes();

        return routeInfoRepository.findAll()
                .map(routeInfo -> routesBuilder.route(predicateSpec -> setPredicateSpec(routeInfo, predicateSpec)))
                .collectList()
                .flatMapMany(objects -> routesBuilder.build().getRoutes());
    }

    private Buildable<Route> setPredicateSpec(RouteInfo routeInfo, PredicateSpec predicateSpec) {
        return predicateSpec
                .path("/" + routeInfo.getCode() + "/**")
                .filters(f -> f.rewritePath("/" + routeInfo.getCode() + "/(?<segment>.*)", "/${segment}"))
                .uri(routeInfo.getProxyHost());
    }
}
