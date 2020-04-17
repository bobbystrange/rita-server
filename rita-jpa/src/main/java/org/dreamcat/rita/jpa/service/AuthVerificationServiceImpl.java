package org.dreamcat.rita.jpa.service;

import org.dreamcat.common.web.util.JacksonUtil;
import org.dreamcat.rita.core.PasswordResetEntity;
import org.dreamcat.rita.core.RegisterEntity;
import org.dreamcat.rita.service.AuthVerificationService;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Create by tuke on 2020/3/12
 */
@Service
public class AuthVerificationServiceImpl implements AuthVerificationService {
    private static final String AUTH_USERNAME_IMAGE_CODE_HASH = "rita:jpa:auth:username:image_code:hash";
    private static final String AUTH_USERNAME_REGISTER_HASH = "rita:jpa:auth:username:register:hash";
    private static final String AUTH_USERNAME_PASSWORD_RESET_HASH = "rita:jpa:auth:username:password_reset:hash";
    private StringRedisTemplate redisTemplate;

    public AuthVerificationServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String releaseImageCode(String email) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(AUTH_USERNAME_IMAGE_CODE_HASH);
        String value = ops.get(email);
        ops.delete(email);
        return value;
    }

    @Override
    public void sealImageCode(String email, String imageCode) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(AUTH_USERNAME_IMAGE_CODE_HASH);
        ops.put(email, imageCode);
    }

    @Override
    public RegisterEntity releaseRegister(String username) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(AUTH_USERNAME_REGISTER_HASH);
        RegisterEntity value = JacksonUtil.fromJson(ops.get(username), RegisterEntity.class);
        ops.delete(username);
        return value;
    }

    @Override
    public void sealRegister(String username, RegisterEntity register) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(AUTH_USERNAME_REGISTER_HASH);
        ops.put(username, JacksonUtil.toJson(register));
    }

    @Override
    public PasswordResetEntity releasePasswordReset(String username) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(AUTH_USERNAME_PASSWORD_RESET_HASH);
        PasswordResetEntity value = JacksonUtil.fromJson(ops.get(username), PasswordResetEntity.class);
        ops.delete(username);
        return value;
    }

    @Override
    public void sealPasswordReset(String username, PasswordResetEntity passwordReset) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(AUTH_USERNAME_PASSWORD_RESET_HASH);
        ops.put(username, JacksonUtil.toJson(passwordReset));
    }
}
