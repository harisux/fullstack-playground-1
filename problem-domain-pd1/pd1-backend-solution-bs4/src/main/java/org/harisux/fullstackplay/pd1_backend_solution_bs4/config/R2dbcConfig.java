package org.harisux.fullstackplay.pd1_backend_solution_bs4.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import io.asyncer.r2dbc.mysql.MySqlConnectionFactoryProvider;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

@Configuration
@EnableR2dbcRepositories
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    @Value("${r2dbc.host}")
    private String host;

    @Value("${r2dbc.port}")
    private Integer port;

    @Value("${r2dbc.database}")
    private String database;

    @Value("${r2dbc.username}")
    private String username;

    @Value("${r2dbc.password}")
    private String password;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return new MySqlConnectionFactoryProvider().create(
            ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.HOST, host)
                .option(ConnectionFactoryOptions.PORT, port)
                .option(ConnectionFactoryOptions.DATABASE, database)
                .option(ConnectionFactoryOptions.USER, username)
                .option(ConnectionFactoryOptions.PASSWORD, password)
                .build()
        );
    }
    
}
