package org.dreamcat.rita.config.swagger;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import springfox.documentation.service.ApiInfo;

/**
 * Create by tuke on 2020/2/12
 */
@Data
@Profile({"dev"})
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    private boolean enable = false;
    private String version = ApiInfo.DEFAULT.getVersion();
    private String title = ApiInfo.DEFAULT.getTitle();
    private String description = ApiInfo.DEFAULT.getDescription();
    private String termsOfServiceUrl = ApiInfo.DEFAULT.getTermsOfServiceUrl();
    private String license = ApiInfo.DEFAULT.getLicense();
    private String licenseUrl = ApiInfo.DEFAULT.getLicenseUrl();
    private Contact contact = new Contact();

    @ConditionalOnMissingBean(SwaggerProperties.class)
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Data
    public static class Contact {
        private String name = "";
        private String url = "";
        private String email = "";
    }
}
