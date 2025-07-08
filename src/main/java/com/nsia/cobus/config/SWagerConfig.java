package com.nsia.cobus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SWagerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(
                        """
                                API permettant aux clients NSIA de se connecter et de consulter leurs informations sur les contrats d'assurances auquelles ils ont souscrits.
                                    """)
                        // .version("1.0")
                        .description("Api de consultation et de souscription aux produits d'assurances De NSIA "));
    }
}
