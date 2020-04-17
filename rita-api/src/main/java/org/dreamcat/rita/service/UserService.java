package org.dreamcat.rita.service;

import org.dreamcat.common.web.core.RestBody;
import org.dreamcat.rita.controller.user.UpdateAvatarQuery;
import org.dreamcat.rita.controller.user.UpdateUserQuery;
import org.dreamcat.rita.controller.user.UserView;

/**
 * Create by tuke on 2020/2/27
 */
public interface UserService {

    RestBody<UserView> getUser();

    RestBody<?> updateUser(UpdateUserQuery query);

    RestBody<Boolean> getUsernameValidity(String username);

    RestBody<Boolean> getEmailValidity(String email);

    RestBody<String> getAvatar();

    RestBody<?> updateAvatar(UpdateAvatarQuery query);
}
