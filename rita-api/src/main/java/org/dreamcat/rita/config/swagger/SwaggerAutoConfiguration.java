package org.dreamcat.rita.config.swagger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Create by tuke on 2020/2/13
 * http://localhost:8080/swagger-ui.html
 */
@Profile({"dev"})
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    @Resource
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(swaggerProperties.isEnable());
    }

    private ApiInfo apiInfo() {
        SwaggerProperties.Contact contact = swaggerProperties.getContact();
        return new ApiInfo(
                swaggerProperties.getTitle(), swaggerProperties.getDescription(),
                swaggerProperties.getVersion(), swaggerProperties.getTermsOfServiceUrl(),
                new Contact(contact.getName(), contact.getUrl(), contact.getEmail()),
                swaggerProperties.getLicense(), swaggerProperties.getLicenseUrl(), new ArrayList<>());
    }
}
