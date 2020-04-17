package org.dreamcat.rita.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Create by tuke on 2020/3/11
 */
@Data
@ConfigurationProperties("app.auth")
public class AppProperties {
    private MaxAge maxAge;

    @Data
    public static class MaxAge {
        private long imageCode;
        private long registerAccessToken;
        private long passwordResetAccessToken;
    }
}
