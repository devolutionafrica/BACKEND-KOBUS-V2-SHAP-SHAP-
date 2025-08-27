package com.nsia.cobus.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.nsia.cobus.infrastucture.ConventionRepository;

@Configuration
public class ConventionRepositoryConfig {

    @Bean
    ConventionRepository conventionRepository(
            @Qualifier("primaryJdbcTemplate") JdbcTemplate jdbcTemplate,
            @Qualifier("secondaryJdbcTemplate") JdbcTemplate jdbcTemplate2) {
        return new ConventionRepository(jdbcTemplate, jdbcTemplate2);
    }

}
