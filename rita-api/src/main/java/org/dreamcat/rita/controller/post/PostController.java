package org.dreamcat.rita.controller.post;

import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.web.core.PageQuery;
import org.dreamcat.common.web.core.RestBody;
import org.dreamcat.rita.config.AppConfig;
import org.dreamcat.rita.service.PostService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Create by tuke on 2020/3/1
 */
@Slf4j
@RestController
@RequestMapping(path = AppConfig.API_PREFIX)
public class PostController {

    @Resource
    private PostService postService;

    @RequestMapping(path = "/post", method = {RequestMethod.POST})
    public RestBody<?> createPost(
            @Valid @RequestBody PostQuery query) {
        return postService.createPost(query);
    }

    @RequestMapping(path = "/post/{id}", method = {RequestMethod.DELETE})
    public RestBody<?> deletePost(@PathVariable long id) {
        return postService.deletePost(id);
    }

    @RequestMapping(path = "/post/{id}", method = {RequestMethod.GET})
    public RestBody<?> getPost(@PathVariable long id) {
        return postService.getPost(id);
    }

    @RequestMapping(path = "/post/{id}", method = {RequestMethod.PUT})
    public RestBody<?> updatePost(
            @PathVariable long id,
            @Valid @RequestBody PostQuery query) {
        return postService.updatePost(id, query);
    }

    ///

    @RequestMapping(path = "/tag", method = {RequestMethod.POST})
    public RestBody<?> createTag(
            @Valid @RequestBody TagQuery query) {
        return postService.createTag(query);
    }

    @RequestMapping(path = "/tag/{id}", method = {RequestMethod.DELETE})
    public RestBody<?> deleteTag(@PathVariable long id) {
        return postService.deleteTag(id);
    }

    @RequestMapping(path = "/tag/{id}", method = {RequestMethod.GET})
    public RestBody<?> getTag(
            @PathVariable long id, PageQuery params) {
        return postService.getTag(id, params);
    }

    @RequestMapping(path = "/tag/{id}", method = {RequestMethod.PUT})
    public RestBody<?> updateTag(
            @PathVariable long id,
            @Valid @RequestBody TagQuery query) {
        return postService.updateTag(id, query);
    }

    ///

    @RequestMapping(path = "/post/validity/name/{name:.{4,128}}", method = {RequestMethod.GET})
    public RestBody<?> getPostNameValidity(@PathVariable String name) {
        return postService.getPostNameValidity(name);
    }

    @RequestMapping(path = "/tag/validity/name/{name:.{4,128}}", method = {RequestMethod.GET})
    public RestBody<?> getTagNameValidity(@PathVariable String name) {
        return postService.getTagNameValidity(name);
    }

    @RequestMapping(path = "/tag/{tid}/remove/post/{pid}", method = {RequestMethod.DELETE})
    public RestBody<?> removePostFromTag(@PathVariable long tid, @PathVariable long pid) {
        return postService.removePostFromTag(tid, pid);
    }

    ///

    @RequestMapping(path = "/post/list", method = {RequestMethod.GET})
    public RestBody<?> getPostList(@Valid PageQuery params) {
        return postService.getPostList(params);
    }

    @RequestMapping(path = "/tag/list", method = {RequestMethod.GET})
    public RestBody<?> getTagList() {
        return postService.getTagList();
    }

    @RequestMapping(path = "/post/favorite/list", method = {RequestMethod.GET})
    public RestBody<?> getPostFavoriteList(@Valid PageQuery query) {
        return postService.getPostFavoriteList(query);
    }

    @RequestMapping(path = "/post/deleted/list", method = {RequestMethod.GET})
    public RestBody<?> getPostDeletedList(@Valid PageQuery query) {
        return postService.getPostDeletedList(query);
    }

    @RequestMapping(path = "/post/restore/{id}", method = {RequestMethod.POST})
    public RestBody<?> restorePost(@PathVariable long id) {
        return postService.restorePost(id);
    }

    @RequestMapping(path = "/post/restore/{id}", method = {RequestMethod.DELETE})
    public RestBody<?> deleteRestorePost(@PathVariable long id) {
        return postService.deletedRestorePost(id);
    }

}
