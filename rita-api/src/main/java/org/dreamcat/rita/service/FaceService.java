package org.dreamcat.rita.service;

import org.dreamcat.common.web.core.PageQuery;
import org.dreamcat.common.web.core.PageView;
import org.dreamcat.common.web.core.RestBody;
import org.dreamcat.rita.controller.face.FaceImView;
import org.dreamcat.rita.controller.face.FaceUserView;
import org.dreamcat.rita.view.PostFatView;
import org.dreamcat.rita.view.TagView;

import java.util.List;

/**
 * Create by tuke on 2020/2/23
 */
public interface FaceService {

    RestBody<FaceUserView> getSetting(String username);

    RestBody<PageView<FaceImView>> getPagePostIm(PageQuery params, String username);

    RestBody<PageView<FaceImView>> getPagePostImByTag(PageQuery params, String tag, String username);

    RestBody<PostFatView> getPost(long id, String username);

    RestBody<PostFatView> getPostByName(String name, String username);

    RestBody<List<TagView>> getTags(String username);
}
