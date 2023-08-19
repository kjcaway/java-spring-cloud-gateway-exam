package me.javaexample.scgdemo.db;

import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.ZoneId;

@Configuration
public class DbConfig {
    @Value("${db.username:}")
    private String username;
    @Value("${db.password:}")
    private String password;
    @Value("${db.dbname:}")
    private String dbname;

    @Bean
    public ConnectionFactory connectionFactory() {
        MySqlConnectionConfiguration options = MySqlConnectionConfiguration.builder()
                .host("127.0.0.1")
                .user(username)
                .port(3306)
                .password(password)
                .database(dbname)
                .serverZoneId(ZoneId.of("Asia/Seoul"))
                .connectTimeout(Duration.ofSeconds(3))
                .socketTimeout(Duration.ofSeconds(4))
                .build();

        return MySqlConnectionFactory.from(options);
    }
}
