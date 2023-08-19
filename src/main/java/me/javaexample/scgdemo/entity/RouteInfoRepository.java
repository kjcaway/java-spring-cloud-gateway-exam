package me.javaexample.scgdemo.entity;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteInfoRepository extends ReactiveCrudRepository<RouteInfo, Integer> {
}
