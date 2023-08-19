package me.javaexample.scgdemo.route;

import me.javaexample.scgdemo.entity.RouteInfo;
import me.javaexample.scgdemo.entity.RouteInfoRepository;
import me.javaexample.scgdemo.filter.AuthFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import reactor.core.publisher.Flux;


public class DynamicRouteLocator implements RouteLocator {

    private final RouteLocatorBuilder builder;
    private final RouteInfoRepository routeInfoRepository;
    private final AuthFilter authFilter;

    public DynamicRouteLocator(RouteLocatorBuilder builder, RouteInfoRepository routeInfoRepository, AuthFilter authFilter) {
        this.builder = builder;
        this.routeInfoRepository = routeInfoRepository;
        this.authFilter = authFilter;
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
                .filters(f -> {
                    if (routeInfo.getIsAuth() == 1) {
                        f.filter(authFilter.apply(new AuthFilter.Config()));
                    }
                    f.rewritePath("/" + routeInfo.getCode() + "/(?<segment>.*)", "/${segment}");
                    return f;
                })
                .uri(routeInfo.getProxyHost());
    }
}
