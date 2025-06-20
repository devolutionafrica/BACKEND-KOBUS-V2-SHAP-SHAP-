package com.nsia.cobus.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.nsia.cobus.infrastucture.ContratRepository;

@Configuration
public class ContratRepositoryConfig {

    @Bean
    ContratRepository contratRepository(
            @Qualifier("primaryJdbcTemplate") JdbcTemplate jdbcTemplate,
            @Qualifier("secondaryJdbcTemplate") JdbcTemplate jdbcTemplate2) {
        return new ContratRepository(jdbcTemplate, jdbcTemplate2);
    }

}
