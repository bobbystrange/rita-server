package org.dreamcat.rita.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.web.core.RestBody;
import org.dreamcat.rita.config.AppConfig;
import org.dreamcat.rita.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Create by tuke on 2020/2/27
 */
@Slf4j
@RestController
@Validated
@RequestMapping(path = AppConfig.API_PREFIX + "/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(method = {RequestMethod.GET})
    public RestBody<?> getUser() {
        return userService.getUser();
    }

    @RequestMapping(method = {RequestMethod.PUT})
    public RestBody<?> updateUser(
            @Valid @RequestBody UpdateUserQuery query) {
        return userService.updateUser(query);
    }

    @RequestMapping(path = "/validity/username/{username}", method = {RequestMethod.GET})
    public RestBody<?> getUsernameValidity(
            @PathVariable String username) {
        return userService.getUsernameValidity(username);
    }

    @RequestMapping(path = "/validity/email/{email}", method = {RequestMethod.GET})
    public RestBody<?> getEmailValidity(
            @PathVariable String email) {
        return userService.getEmailValidity(email);
    }

    @RequestMapping(path = "/avatar", method = {RequestMethod.GET})
    public RestBody<?> getAvatar() {
        return userService.getAvatar();
    }

    @RequestMapping(path = "/avatar", method = {RequestMethod.POST})
    public RestBody<?> updateAvatar(
            @Valid @RequestBody UpdateAvatarQuery query) {
        return userService.updateAvatar(query);
    }

}
