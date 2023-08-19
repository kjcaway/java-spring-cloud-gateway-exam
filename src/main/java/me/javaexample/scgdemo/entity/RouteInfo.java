package me.javaexample.scgdemo.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("tbl_route_info")
public class RouteInfo {
    @Id
    private Integer id;
    private String code;
    private String proxyHost;
    private Integer isAuth;

    public String getCode() {
        return this.code;
    }

    public String getProxyHost() {
        return this.proxyHost;
    }

    public Integer getIsAuth() {
        return this.isAuth;
    }
}
