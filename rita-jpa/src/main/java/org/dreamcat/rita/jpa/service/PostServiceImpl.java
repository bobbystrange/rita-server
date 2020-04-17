package org.dreamcat.rita.jpa.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.web.core.PageQuery;
import org.dreamcat.common.web.core.PageView;
import org.dreamcat.common.web.core.RestBody;
import org.dreamcat.common.web.exception.ForbiddenException;
import org.dreamcat.common.web.util.BeanCopierUtil;
import org.dreamcat.rita.controller.face.FaceImView;
import org.dreamcat.rita.controller.post.PostQuery;
import org.dreamcat.rita.controller.post.TagQuery;
import org.dreamcat.rita.jpa.dao.DeletedPostDao;
import org.dreamcat.rita.jpa.dao.PostDao;
import org.dreamcat.rita.jpa.dao.PostTagDao;
import org.dreamcat.rita.jpa.dao.TagDao;
import org.dreamcat.rita.jpa.entity.DeletedPostEntity;
import org.dreamcat.rita.jpa.entity.PostEntity;
import org.dreamcat.rita.jpa.entity.PostTagEntity;
import org.dreamcat.rita.jpa.entity.TagEntity;
import org.dreamcat.rita.service.CacheService;
import org.dreamcat.rita.service.PostService;
import org.dreamcat.rita.view.PostThinView;
import org.dreamcat.rita.view.PostView;
import org.dreamcat.rita.view.TagFatView;
import org.dreamcat.rita.view.TagView;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by tuke on 2020/3/10
 */
@Slf4j
@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private PostDao postDao;
    private PostTagDao postTagDao;
    private TagDao tagDao;
    private DeletedPostDao deletedPostDao;
    private CacheService cacheService;
    private CommonService commonService;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public RestBody<String> createPost(PostQuery query) {
        long uid = retrieveUid();
        Long pid = cacheService.findPostIdByUidAndName(uid, query.getName());
        if (pid != null) {
            return RestBody.error("Post '%s' already exists", query.getName());
        }

        PostEntity post = BeanCopierUtil.copy(query, PostEntity.class);
        long timestamp = System.currentTimeMillis();
        post.setCtime(timestamp);
        post.setMtime(timestamp);
        post.setUid(uid);
        pid = postDao.saveAndFlush(post).getId();
        updateTag(query.getTags(), uid, pid, timestamp);
        return RestBody.ok(String.valueOf(pid));
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public RestBody<?> deletePost(long id) {
        PostEntity post = postDao.findById(id).orElse(null);
        if (post == null) {
            return RestBody.error("Post %d doesn't exist", id);
        }
        checkPermissions(post);

        postDao.deleteById(id);
        DeletedPostEntity deletedPost = BeanCopierUtil.copy(post, DeletedPostEntity.class);
        deletedPostDao.save(deletedPost);
        return RestBody.ok();
    }

    @Override
    public RestBody<PostView> getPost(long id) {
        PostEntity post = postDao.findById(id).orElse(null);
        if (post == null) {
            return RestBody.error("Post %d doesn't exist", id);
        }
        checkPermissions(post);

        PostView view = BeanCopierUtil.copy(post, PostView.class);
        commonService.fillTags(view);
        return RestBody.ok(view);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public RestBody<?> updatePost(long id, PostQuery query) {
        long uid = retrieveUid();
        PostEntity post = postDao.findById(id).orElse(null);
        if (post == null) {
            return RestBody.error("Post %d doesn't exist", id);
        }
        checkPermissions(post);

        long timestamp = System.currentTimeMillis();
        BeanCopierUtil.copy(query, post);
        post.setMtime(timestamp);
        postDao.save(post);
        updateTag(query.getTags(), uid, id, timestamp);

        return RestBody.ok();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public RestBody<String> createTag(TagQuery query) {
        long uid = retrieveUid();
        String name = query.getName();
        Long tid = cacheService.findTagIdByUidAndName(uid, name);
        if (tid != null) {
            return RestBody.error("Tag '%s' already exists", name);
        }

        long timestamp = System.currentTimeMillis();
        TagEntity tag = BeanCopierUtil.copy(query, TagEntity.class);
        tag.setCtime(timestamp);
        tag.setMtime(timestamp);
        tag.setUid(uid);
        tid = tagDao.saveAndFlush(tag).getId();
        return RestBody.ok(String.valueOf(tid));
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public RestBody<?> deleteTag(long id) {
        TagEntity tag = tagDao.findById(id).orElse(null);
        if (tag == null) {
            return RestBody.error("Tag %d doesn't exist", id);
        }
        checkPermissions(tag);

        tagDao.deleteById(id);
        postTagDao.deleteInBatch(postTagDao.findAllByTid(id));
        return RestBody.ok();
    }

    @Override
    public RestBody<TagFatView> getTag(long id, PageQuery query) {
        TagEntity tag = tagDao.findById(id).orElse(null);
        if (tag == null) {
            return RestBody.error("Tag %d doesn't exist", id);
        }
        long uid = checkPermissions(tag);
        TagFatView view = BeanCopierUtil.copy(tag, TagFatView.class);
        long total = postDao.countByUidAndTid(uid, id);
        view.setCount(total);

        List<Long> pids = postDao.findImsByUidAndTid(uid, id, query).stream()
                .map(FaceImView::getId).collect(Collectors.toList());

        List<PostThinView> posts = postDao.findAllById(pids).stream()
                .map(it -> BeanCopierUtil.copy(it, PostThinView.class))
                .collect(Collectors.toList());
        view.setPosts(new PageView<>(posts, total, query));
        return RestBody.ok(view);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public RestBody<?> updateTag(long id, TagQuery query) {
        TagEntity tag = tagDao.findById(id).orElse(null);
        if (tag == null) {
            return RestBody.error("Tag %d doesn't exist", id);
        }
        checkPermissions(tag);

        BeanCopierUtil.copy(query, tag);
        tagDao.save(tag);
        return RestBody.ok();
    }

    @Override
    public RestBody<Boolean> getPostNameValidity(String name) {
        long uid = retrieveUid();
        Long pid = cacheService.findPostIdByUidAndName(uid, name);
        return RestBody.ok(pid == null);
    }

    @Override
    public RestBody<Boolean> getTagNameValidity(String name) {
        long uid = retrieveUid();
        Long tid = cacheService.findTagIdByUidAndName(uid, name);
        return RestBody.ok(tid == null);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public RestBody<?> removePostFromTag(long tid, long pid) {
        PostTagEntity postTag = postTagDao.findByPidAndTid(pid, tid);
        if (postTag == null) {
            return RestBody.error("Post %d doesn't have tag %d", pid, tid);
        }
        checkPermissions(postTag);
        postTagDao.deleteById(postTag.getId());
        return RestBody.ok();
    }

    @Override
    public RestBody<PageView<PostView>> getPostList(PageQuery query) {
        long uid = retrieveUid();
        List<PostView> items = postDao.findAllByUid(uid, query).stream()
                .map(it -> {
                    PostView view = BeanCopierUtil.copy(it, PostView.class);
                    commonService.fillTags(view);
                    return view;
                })
                .collect(Collectors.toList());
        long total = postDao.countByUid(uid);
        return RestBody.ok(new PageView<>(items, total, query));
    }

    @Override
    public RestBody<PageView<PostView>> getPostFavoriteList(PageQuery query) {
        long uid = retrieveUid();
        List<PostView> items = postDao.findFavoriteByUid(uid, query).stream()
                .map(it -> {
                    PostView view = BeanCopierUtil.copy(it, PostView.class);
                    commonService.fillTags(view);
                    return view;
                })
                .collect(Collectors.toList());
        long total = postDao.countFavoriteByUid(uid);
        return RestBody.ok(new PageView<>(items, total, query));
    }

    @Override
    public RestBody<PageView<PostThinView>> getPostDeletedList(PageQuery params) {
        long uid = retrieveUid();
        int size = params.getSize();
        int offset = (params.getPage() - 1) * size;
        List<PostThinView> items = deletedPostDao.findAllByUid(uid, offset, size).stream()
                .map(it -> BeanCopierUtil.copy(it, PostThinView.class))
                .collect(Collectors.toList());
        long total = deletedPostDao.countAllByUid(uid);
        return RestBody.ok(new PageView<>(items, total, params));
    }

    @Override
    public RestBody<List<TagView>> getTagList() {
        long uid = retrieveUid();
        List<TagView> data = tagDao.findAllByUid(uid).stream()
                .map(it -> {
                    TagView tag = BeanCopierUtil.copy(it, TagView.class);
                    tag.setCount(postDao.countByUidAndTid(uid, it.getId()));
                    return tag;
                })
                .collect(Collectors.toList());
        return RestBody.ok(data);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public RestBody<?> restorePost(long id) {
        DeletedPostEntity deletedPost = deletedPostDao.findById(id).orElse(null);
        if (deletedPost == null) {
            return RestBody.error("Deleted Post %d doesn't exist");
        }
        checkPermissions(deletedPost);

        PostEntity post = BeanCopierUtil.copy(deletedPost, PostEntity.class);
        post.setMtime(System.currentTimeMillis());
        postDao.save(post);
        deletedPostDao.deleteById(id);
        return RestBody.ok();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public RestBody<?> deletedRestorePost(long id) {
        DeletedPostEntity deletedPost = deletedPostDao.findById(id).orElse(null);
        if (deletedPost == null) {
            return RestBody.error("Deleted Post %d doesn't exist");
        }
        checkPermissions(deletedPost);

        deletedPostDao.deleteById(id);
        postTagDao.deleteInBatch(postTagDao.findAllByPid(id));
        return RestBody.ok();
    }

    private void updateTag(List<String> newNames, long uid, long pid, long timestamp) {
        if (newNames == null) return;
        List<PostTagEntity> oldPostTagEntities = postTagDao.findAllByPid(pid);
        if (!oldPostTagEntities.isEmpty()) postTagDao.deleteInBatch(oldPostTagEntities);
        if (newNames.isEmpty()) return;

        List<String> allNames = tagDao.findAllByUid(uid).stream()
                .map(TagEntity::getName).collect(Collectors.toList());
        List<String> missingNames = new ArrayList<>(newNames);
        missingNames.removeAll(allNames);
        if (!missingNames.isEmpty()) {
            List<TagEntity> missingTagEntities = missingNames.stream().map(name -> {
                TagEntity tag = new TagEntity();
                tag.setCtime(timestamp);
                tag.setMtime(timestamp);
                tag.setName(name);
                tag.setUid(uid);
                return tag;
            }).collect(Collectors.toList());
            try {
                tagDao.saveAll(missingTagEntities);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        List<Long> newTids = tagDao.findAllByUid(uid).stream()
                .filter(it -> newNames.contains(it.getName()))
                .map(TagEntity::getId).collect(Collectors.toList());

        List<PostTagEntity> newPostTagEntities = newTids.stream().map(tid -> {
            PostTagEntity postTag = new PostTagEntity();
            postTag.setCtime(timestamp);
            postTag.setMtime(timestamp);
            postTag.setUid(uid);
            postTag.setPid(pid);
            postTag.setTid(tid);
            return postTag;
        }).collect(Collectors.toList());
        postTagDao.saveAll(newPostTagEntities);
    }

    private long retrieveUid() {
        return commonService.retrieveUid();
    }

    private long checkPermissions(TagEntity entity) {
        long uid = retrieveUid();
        if (uid != entity.getUid()) {
            throw new ForbiddenException();
        }
        return uid;
    }

    private void checkPermissions(PostEntity entity) {
        long uid = retrieveUid();
        if (uid != entity.getUid()) {
            throw new ForbiddenException();
        }
    }

    private void checkPermissions(PostTagEntity entity) {
        long uid = retrieveUid();
        if (uid != entity.getUid()) {
            throw new ForbiddenException();
        }
    }

    private void checkPermissions(DeletedPostEntity entity) {
        long uid = retrieveUid();
        if (uid != entity.getUid()) {
            throw new ForbiddenException();
        }
    }
}
