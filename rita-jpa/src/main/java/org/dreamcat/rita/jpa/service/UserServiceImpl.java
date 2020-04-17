package org.dreamcat.rita.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.web.core.RestBody;
import org.dreamcat.common.web.util.BeanCopierUtil;
import org.dreamcat.rita.controller.user.UpdateAvatarQuery;
import org.dreamcat.rita.controller.user.UpdateUserQuery;
import org.dreamcat.rita.controller.user.UserView;
import org.dreamcat.rita.jpa.dao.AvatarDao;
import org.dreamcat.rita.jpa.dao.PostDao;
import org.dreamcat.rita.jpa.dao.TagDao;
import org.dreamcat.rita.jpa.dao.UserDao;
import org.dreamcat.rita.jpa.entity.AvatarEntity;
import org.dreamcat.rita.jpa.entity.Gender;
import org.dreamcat.rita.jpa.entity.UserEntity;
import org.dreamcat.rita.service.CacheService;
import org.dreamcat.rita.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Create by tuke on 2020/3/10
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private AvatarDao avatarDao;
    private PostDao postDao;
    private CacheService cacheService;
    private CommonService commonService;

    public UserServiceImpl(UserDao userDao, AvatarDao avatarDao, PostDao postDao, TagDao tagDao, CacheService cacheService, CommonService commonService) {
        this.userDao = userDao;
        this.avatarDao = avatarDao;
        this.postDao = postDao;
        this.cacheService = cacheService;
        this.commonService = commonService;
    }

    @Override
    public RestBody<UserView> getUser() {
        long uid = retrieveUid();
        UserEntity user = userDao.findById(uid).orElse(null);
        if (user == null) {
            return RestBody.error("User %d doesn't exist", uid);
        }

        UserView view = BeanCopierUtil.copy(user, UserView.class);
        view.setUsername(user.getName());
        view.setGender(Gender.format(user.getGender()));
        view.setPostCount(postDao.countByUid(uid));
        view.setFavoritePostCount(postDao.countFavoriteByUid(uid));
        return RestBody.ok(view);
    }

    @Override
    public RestBody<?> updateUser(UpdateUserQuery query) {
        String name = query.getUsername();
        UserEntity user = userDao.findByName(name);
        if (user == null) {
            return RestBody.error("User %d doesn't exist", name);
        }
        BeanCopierUtil.copy(query, user);
        user.setGender(Gender.from(query.getGender()));
        userDao.save(user);
        return RestBody.ok();
    }

    @Override
    public RestBody<Boolean> getUsernameValidity(String username) {
        Long uid = cacheService.findUserIdByName(username);
        return RestBody.ok(uid == null);
    }

    @Override
    public RestBody<Boolean> getEmailValidity(String email) {
        return RestBody.ok();
    }

    @Override
    public RestBody<String> getAvatar() {
        long uid = retrieveUid();
        AvatarEntity avatar = avatarDao.findByUid(uid);
        return RestBody.ok(avatar != null ? avatar.getAvatar() : "");
    }

    @Override
    public RestBody<?> updateAvatar(UpdateAvatarQuery query) {
        long uid = retrieveUid();
        long timestamp = System.currentTimeMillis();

        AvatarEntity avatar = avatarDao.findByUid(uid);
        if (avatar == null) {
            avatar = new AvatarEntity();
            avatar.setCtime(timestamp);
            avatar.setUid(uid);
        }

        avatar.setMtime(timestamp);
        avatar.setAvatar(query.getAvatar());
        avatarDao.save(avatar);
        return RestBody.ok();
    }

    private long retrieveUid() {
        return commonService.retrieveUid();
    }
}
