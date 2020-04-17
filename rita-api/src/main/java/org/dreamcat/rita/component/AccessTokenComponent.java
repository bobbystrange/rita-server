package org.dreamcat.rita.component;

import org.dreamcat.common.crypto.CryptoUtil;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.common.web.jwt.JwtProperties;
import org.dreamcat.common.web.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Create by tuke on 2020/3/11
 */
@Component
public class AccessTokenComponent {
    private JwtProperties jwtProperties;

    public AccessTokenComponent(
            @Autowired JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public <T> String encode(T data) {
        return CryptoUtil.digestHMACSHA512(
                JacksonUtil.toJson(data), jwtProperties.getSecretKey());
    }

    public <T> boolean isInvalid(T data, String accessToken) {
        ObjectUtil.requireNotBlank(accessToken, "data");
        String signature = CryptoUtil.digestHMACSHA512(
                JacksonUtil.toJson(data), jwtProperties.getSecretKey());
        return !accessToken.equals(signature);
    }
}
