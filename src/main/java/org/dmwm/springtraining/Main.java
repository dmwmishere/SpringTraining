package org.dmwm.springtraining;

import com.google.common.base.Predicate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Set;

@SpringBootApplication
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class Main {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .tags(new Tag("Client Entity",
                        "Сущность клиента банка"))
                .tags(new Tag("Account Entity",
                        "Сущность счета клиента"))
                .tags(new Tag("Bank Entity",
                        "Сущность банка"))
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Bank Application REST API",
                "Домашняя работа по курсу Spring",
                "1.0",
                null,
                null,
                null,
                null,
                new ArrayList<>());
        return apiInfo;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
