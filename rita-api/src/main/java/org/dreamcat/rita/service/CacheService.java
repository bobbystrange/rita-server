package org.dreamcat.rita.service;

import org.dreamcat.common.web.exception.NotFoundException;

/**
 * Create by tuke on 2020/3/10
 */
public interface CacheService {

    default long mapNameToUserId(String name) {
        Long id = findUserIdByName(name);
        if (id == null) {
            throw new NotFoundException(String.format("User '%s' doesn't exist", name));
        }
        return id;
    }

    Long findUserIdByName(String name);

    default long mapUidAndNameToPostId(long uid, String name) {
        Long id = findPostIdByUidAndName(uid, name);
        if (id == null) {
            throw new NotFoundException(String.format("Post '%s' doesn't exist", name));
        }
        return id;
    }

    Long findPostIdByUidAndName(long uid, String name);

    default long mapUidAndNameToTagId(long id, String name) {
        Long uid = findTagIdByUidAndName(id, name);
        if (uid == null) {
            throw new NotFoundException(String.format("Tag '%s' doesn't exist", name));
        }
        return uid;
    }

    Long findTagIdByUidAndName(long id, String name);
}
