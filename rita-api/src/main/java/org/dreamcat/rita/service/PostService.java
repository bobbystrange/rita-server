package org.dreamcat.rita.service;

import org.dreamcat.common.web.core.PageQuery;
import org.dreamcat.common.web.core.PageView;
import org.dreamcat.common.web.core.RestBody;
import org.dreamcat.rita.controller.post.PostQuery;
import org.dreamcat.rita.controller.post.TagQuery;
import org.dreamcat.rita.view.PostThinView;
import org.dreamcat.rita.view.PostView;
import org.dreamcat.rita.view.TagFatView;
import org.dreamcat.rita.view.TagView;

import java.util.List;

/**
 * Create by tuke on 2020/3/1
 */
public interface PostService {

    RestBody<String> createPost(PostQuery query);

    RestBody<?> deletePost(long id);

    RestBody<PostView> getPost(long id);

    RestBody<?> updatePost(long id, PostQuery query);

    ///

    RestBody<String> createTag(TagQuery query);

    RestBody<?> deleteTag(long id);

    RestBody<TagFatView> getTag(long id, PageQuery query);

    RestBody<?> updateTag(long id, TagQuery query);

    ///

    RestBody<Boolean> getPostNameValidity(String name);

    RestBody<Boolean> getTagNameValidity(String name);

    RestBody<?> removePostFromTag(long tid, long pid);

    ///

    RestBody<PageView<PostView>> getPostList(PageQuery query);

    RestBody<PageView<PostView>> getPostFavoriteList(PageQuery query);

    RestBody<PageView<PostThinView>> getPostDeletedList(PageQuery query);

    RestBody<List<TagView>> getTagList();

    RestBody<?> restorePost(long id);

    RestBody<?> deletedRestorePost(long id);
}
