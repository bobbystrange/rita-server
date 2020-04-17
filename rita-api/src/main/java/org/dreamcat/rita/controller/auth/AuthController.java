package org.dreamcat.rita.controller.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.web.core.RestBody;
import org.dreamcat.common.webmvc.security.JwtServletFactory;
import org.dreamcat.rita.component.AuthComponent;
import org.dreamcat.rita.config.AppConfig;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Create by tuke on 2020/2/26
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = AppConfig.API_PREFIX + "/auth", method = {RequestMethod.POST},
        consumes = {MediaType.APPLICATION_JSON_VALUE})
public class AuthController {
    private AuthComponent<?> authComponent;
    private JwtServletFactory jwtFactory;

    @RequestMapping(path = "/register")
    public RestBody<?> register(
            @Valid @RequestBody RegisterQuery query) {
        return authComponent.register(query);
    }

    @RequestMapping(path = "/register/confirm")
    public RestBody<?> registerConfirm(
            @Valid @RequestBody RegisterConfirmQuery query) {
        return authComponent.registerConfirm(query);
    }

    @RequestMapping(path = "/login")
    public RestBody<?> login(
            @Valid @RequestBody LoginQuery query) {
        return authComponent.login(query);
    }

    @RequestMapping(path = "/password-reset", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public RestBody<?> passwordReset(
            @Valid @RequestBody PasswordResetQuery query) {
        return authComponent.passwordReset(query);
    }

    @RequestMapping(path = "/password-reset/confirm")
    public RestBody<?> passwordResetConfirm(
            @Valid @RequestBody PasswordResetConfirmQuery query) {
        return authComponent.passwordResetConfirm(query);
    }

    @RequestMapping(path = "/code/image")
    public RestBody<?> fetchImageCode(
            @Valid @RequestBody ImageCodeQuery query) {
        return authComponent.fetchImageCode(query);
    }
}
