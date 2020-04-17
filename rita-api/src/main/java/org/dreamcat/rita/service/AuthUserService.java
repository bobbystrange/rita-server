package org.dreamcat.rita.service;

import org.dreamcat.rita.core.RegisterEntity;

/**
 * Create by tuke on 2020/3/21
 */
public interface AuthUserService<T> {
    T findByName(String name);

    String destructureAsEncodedPassword(T user);

    String destructureAsSubject(T user);

    void createUser(String username, RegisterEntity register);

    void updateUserPassword(T user, String password);

}
