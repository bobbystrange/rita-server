package org.dreamcat.rita.service;

import org.dreamcat.rita.core.PasswordResetEntity;
import org.dreamcat.rita.core.RegisterEntity;

/**
 * Create by tuke on 2020/3/12
 */
public interface AuthVerificationService {

    String releaseImageCode(String proof);

    void sealImageCode(String proof, String imageCode);

    RegisterEntity releaseRegister(String username);

    void sealRegister(String username, RegisterEntity register);

    PasswordResetEntity releasePasswordReset(String username);

    void sealPasswordReset(String username, PasswordResetEntity passwordReset);
}
