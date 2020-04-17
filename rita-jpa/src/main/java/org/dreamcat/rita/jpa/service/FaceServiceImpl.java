package org.dreamcat.rita.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.web.core.PageQuery;
import org.dreamcat.common.web.core.PageView;
import org.dreamcat.common.web.core.RestBody;
import org.dreamcat.common.web.util.BeanCopierUtil;
import org.dreamcat.rita.controller.face.FaceImView;
import org.dreamcat.rita.controller.face.FaceUserView;
import org.dreamcat.rita.jpa.dao.AvatarDao;
import org.dreamcat.rita.jpa.dao.PostDao;
import org.dreamcat.rita.jpa.dao.PostTagDao;
import org.dreamcat.rita.jpa.dao.TagDao;
import org.dreamcat.rita.jpa.dao.UserDao;
import org.dreamcat.rita.jpa.entity.AvatarEntity;
import org.dreamcat.rita.jpa.entity.PostEntity;
import org.dreamcat.rita.jpa.entity.UserEntity;
import org.dreamcat.rita.service.CacheService;
import org.dreamcat.rita.service.FaceService;
import org.dreamcat.rita.view.PostFatView;
import org.dreamcat.rita.view.PostThinView;
import org.dreamcat.rita.view.TagView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by tuke on 2020/2/23
 */
@Slf4j
@Service
public class FaceServiceImpl implements FaceService {
    private UserDao userDao;
    private AvatarDao avatarDao;
    private PostDao postDao;
    private CacheService cacheService;
    private CommonService commonService;

    public FaceServiceImpl(
            @Autowired UserDao userDao,
            @Autowired AvatarDao avatarDao,
            @Autowired PostDao postDao,
            @Autowired TagDao tagDao,
            @Autowired PostTagDao postTagDao,
            @Autowired CacheService cacheService,
            @Autowired CommonService commonService) {
        this.userDao = userDao;
        this.avatarDao = avatarDao;
        this.postDao = postDao;
        this.cacheService = cacheService;
        this.commonService = commonService;
    }

    @Override
    public RestBody<FaceUserView> getSetting(String username) {
        UserEntity user = userDao.findByName(username);
        if (user == null) {
            return RestBody.error(String.format("User '%s' doesn't exist", username));
        }
        String avatar = avatarDao.findById(user.getId())
                .orElse(new AvatarEntity()).getAvatar();

        FaceUserView view = new FaceUserView();
        view.setUsename(username);
        view.setFullName(user.getFirstName() + " " + user.getLastName());
        view.setStyle(user.getStyle());
        view.setAvatar(avatar);
        return RestBody.ok(view);
    }

    @Override
    public RestBody<PageView<FaceImView>> getPagePostIm(PageQuery query, String username) {
        long uid = cacheService.mapNameToUserId(username);
        List<FaceImView> items = postDao.findImsByUid(uid, query);
        long total = postDao.countByUid(uid);
        return RestBody.ok(new PageView<>(items, total, query));
    }

    @Override
    public RestBody<PageView<FaceImView>> getPagePostImByTag(PageQuery query, String tag, String username) {
        long uid = cacheService.mapNameToUserId(username);
        long tid = cacheService.mapUidAndNameToTagId(uid, tag);
        List<FaceImView> items = postDao.findImsByUidAndTid(uid, tid, query);

        long total = postDao.countByUidAndTid(uid, tid);
        return RestBody.ok(new PageView<>(items, total, query));
    }

    @Override
    public RestBody<PostFatView> getPost(long id, String username) {
        PostEntity post = postDao.findById(id).orElse(null);
        if (post == null) {
            return RestBody.error("Post '%s' doesn't exist", id);
        }
        long pid = post.getId();

        PostFatView view = BeanCopierUtil.copy(post, PostFatView.class);
        commonService.fillTags(view);

        PostEntity prevPost = postDao.findPrevPost(pid);
        PostEntity nextPost = postDao.findNextPost(pid);
        if (prevPost != null) {
            PostThinView prev = BeanCopierUtil.copy(prevPost, PostThinView.class);
            view.setPrev(prev);
        }
        if (nextPost != null) {
            PostThinView next = BeanCopierUtil.copy(nextPost, PostThinView.class);
            view.setNext(next);
        }
        return RestBody.ok(view);
    }

    @Override
    public RestBody<PostFatView> getPostByName(String name, String username) {
        long uid = cacheService.mapNameToUserId(username);
        long pid = cacheService.mapUidAndNameToPostId(uid, name);
        return getPost(pid, username);
    }

    @Override
    public RestBody<List<TagView>> getTags(String username) {
        long uid = cacheService.mapNameToUserId(username);
        return RestBody.ok(commonService.findAllTagsByUid(uid));
    }
}
