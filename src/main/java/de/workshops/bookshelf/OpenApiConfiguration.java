package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
class OpenApiConfiguration {
    @Bean
    OpenAPI api(OpenApiProperties openApiProperties) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(openApiProperties.title())
                                .version(openApiProperties.version())
                                .description("This Bookshelf has a capacity of %d books.".formatted(openApiProperties.capacity()))
                                .license(new License()
                                        .name(openApiProperties.license().name())
                                        .url(openApiProperties.license().url().toString())
                                )
                );
    }
}
