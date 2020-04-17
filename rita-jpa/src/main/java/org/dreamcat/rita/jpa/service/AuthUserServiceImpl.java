package org.dreamcat.rita.jpa.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.web.util.BeanCopierUtil;
import org.dreamcat.rita.core.RegisterEntity;
import org.dreamcat.rita.jpa.dao.AvatarDao;
import org.dreamcat.rita.jpa.dao.UserDao;
import org.dreamcat.rita.jpa.entity.AvatarEntity;
import org.dreamcat.rita.jpa.entity.UserEntity;
import org.dreamcat.rita.service.AuthUserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Create by tuke on 2020/3/21
 */
@Slf4j
@AllArgsConstructor
@Service
public class AuthUserServiceImpl implements AuthUserService<UserEntity> {
    private UserDao userDao;
    private AvatarDao avatarDao;

    @Override
    public UserEntity findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public String destructureAsEncodedPassword(UserEntity user) {
        return user == null ? null : user.getPassword();
    }

    @Override
    public String destructureAsSubject(UserEntity user) {
        return user == null ? null : user.getId().toString();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public void createUser(String username, RegisterEntity register) {
        UserEntity user = BeanCopierUtil.copy(register, UserEntity.class);
        long timestamp = System.currentTimeMillis();
        user.setCtime(timestamp);
        user.setMtime(timestamp);
        user.setName(username);
        long uid = userDao.saveAndFlush(user).getId();

        AvatarEntity avatar = new AvatarEntity();
        avatar.setCtime(timestamp);
        avatar.setMtime(timestamp);
        avatar.setUid(uid);
        avatarDao.save(avatar);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public void updateUserPassword(UserEntity user, String password) {
        user.setMtime(System.currentTimeMillis());
        user.setPassword(password);
        userDao.save(user);
    }
}
