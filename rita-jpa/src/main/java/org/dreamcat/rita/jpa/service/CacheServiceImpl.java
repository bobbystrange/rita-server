package org.dreamcat.rita.jpa.service;

import org.dreamcat.rita.jpa.dao.PostDao;
import org.dreamcat.rita.jpa.dao.TagDao;
import org.dreamcat.rita.jpa.dao.UserDao;
import org.dreamcat.rita.jpa.entity.PostEntity;
import org.dreamcat.rita.jpa.entity.TagEntity;
import org.dreamcat.rita.jpa.entity.UserEntity;
import org.dreamcat.rita.service.CacheService;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Create by tuke on 2020/3/10
 */
@Service
public class CacheServiceImpl implements CacheService {
    private static final String USER_NAME_ID_HASH = "rita:user:name:id:hash";
    private static final String POST_NAME_ID_HASH_PREFIX = "rita:post:name:id:hash:";
    private static final String TAG_NAME_ID_HASH_PREFIX = "rita:tag:name:id:hash:";
    private UserDao userDao;
    private PostDao postDao;
    private TagDao tagDao;
    private StringRedisTemplate redisTemplate;

    public CacheServiceImpl(UserDao userDao, PostDao postDao, TagDao tagDao, StringRedisTemplate redisTemplate) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.tagDao = tagDao;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Long findUserIdByName(String name) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(USER_NAME_ID_HASH);
        String value = ops.get(name);
        if (value != null) return Long.valueOf(value);

        UserEntity user = userDao.findByName(name);
        if (user == null) return null;

        Long id = user.getId();
        ops.putIfAbsent(name, id.toString());
        return id;
    }

    @Override
    public Long findPostIdByUidAndName(long uid, String name) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(POST_NAME_ID_HASH_PREFIX + uid);
        String value = ops.get(name);
        if (value != null) return Long.valueOf(value);

        PostEntity post = postDao.findByUidAndName(uid, name);
        if (post == null) return null;

        Long id = post.getId();
        ops.putIfAbsent(name, id.toString());
        return id;
    }

    @Override
    public Long findTagIdByUidAndName(long uid, String name) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(TAG_NAME_ID_HASH_PREFIX + uid);
        String value = ops.get(name);
        if (value != null) return Long.valueOf(value);

        TagEntity tag = tagDao.findByUidAndName(uid, name);
        if (tag == null) return null;

        Long id = tag.getId();
        ops.putIfAbsent(name, id.toString());
        return id;
    }
}
