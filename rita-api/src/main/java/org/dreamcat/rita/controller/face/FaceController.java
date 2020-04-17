package org.dreamcat.rita.controller.face;

import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.web.core.PageQuery;
import org.dreamcat.common.web.core.RestBody;
import org.dreamcat.rita.config.AppConfig;
import org.dreamcat.rita.service.FaceService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Create by tuke on 2020/2/23
 */
@Slf4j
@RestController
@RequestMapping(path = AppConfig.API_PREFIX + "/face",
        method = {RequestMethod.GET})
public class FaceController {

    @Resource
    private FaceService faceService;

    @RequestMapping(path = "/{username:^[a-zA-Z][a-zA-Z0-9_-]{3,31}$}/setting")
    public RestBody<?> getSetting(
            @PathVariable String username) {
        return faceService.getSetting(username);
    }

    @RequestMapping(path = "/{username:^[a-zA-Z][a-zA-Z0-9_-]{3,31}$}/post/im/page")
    public RestBody<?> getPagePostIm(
            @PathVariable String username,
            @Valid PageQuery params) {
        return faceService.getPagePostIm(params, username);
    }

    @RequestMapping(path = "/{username:^[a-zA-Z][a-zA-Z0-9_-]{3,31}$}/post/im/page/tag/{tag:^[a-zA-Z0-9.-_@#$%&*^<>?;:]{2,32}$}")
    public RestBody<?> getPagePostImByTag(
            @PathVariable String username,
            @PathVariable String tag,
            @Valid PageQuery query) {
        return faceService.getPagePostImByTag(query, tag, username);
    }

    @RequestMapping(path = "/{username:^[a-zA-Z][a-zA-Z0-9_-]{3,31}$}/post/id/{id:^[1-9][0-9]*?$}")
    public RestBody<?> getPost(
            @PathVariable String username,
            @PathVariable long id) {
        return faceService.getPost(id, username);
    }

    @RequestMapping(path = "/{username:^[a-zA-Z][a-zA-Z0-9_-]{3,31}$}/post/name/{name:^.{4,128}$}")
    public RestBody<?> getPostByName(
            @PathVariable String username,
            @PathVariable String name) {
        return faceService.getPostByName(name, username);
    }

    @RequestMapping(path = "/{username:^[a-zA-Z][a-zA-Z0-9_-]{3,31}$}/tag")
    public RestBody<?> getTags(
            @PathVariable String username) {
        return faceService.getTags(username);
    }

}
