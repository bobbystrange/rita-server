package org.dreamcat.rita.config;

import org.dreamcat.common.web.handler.RestExceptionHandler;
import org.dreamcat.common.web.mail.MailTransmitter;
import org.dreamcat.common.web.template.ThymeleafProcesser;
import org.dreamcat.common.webmvc.handler.RestErrorController;
import org.dreamcat.common.webmvc.security.EnableJwtServletSecurity;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Create by tuke on 2020/2/3
 */
@EnableConfigurationProperties({AppProperties.class})
@EnableJwtServletSecurity
@Import({RestErrorController.class, RestExceptionHandler.class,
        MailTransmitter.class, ThymeleafProcesser.class})
@Configuration
public class AppConfig {
    public static final String API_PREFIX = "/api/v1";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
