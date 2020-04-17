package org.dreamcat.rita.jpa.service;

import lombok.AllArgsConstructor;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.common.web.exception.UnauthorizedException;
import org.dreamcat.common.web.util.BeanCopierUtil;
import org.dreamcat.common.webmvc.security.JwtServletFactory;
import org.dreamcat.rita.jpa.dao.PostDao;
import org.dreamcat.rita.jpa.dao.PostTagDao;
import org.dreamcat.rita.jpa.dao.TagDao;
import org.dreamcat.rita.jpa.entity.PostTagEntity;
import org.dreamcat.rita.view.PostView;
import org.dreamcat.rita.view.TagView;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by tuke on 2020/3/12
 */
@AllArgsConstructor
@Service
public class CommonService {
    private PostDao postDao;
    private TagDao tagDao;
    private PostTagDao postTagDao;
    private JwtServletFactory jwtFactory;

    public long retrieveUid() {
        String subject = jwtFactory.getSubject();
        if (subject == null) {
            throw new UnauthorizedException("No subject in current request");
        }
        return Long.parseLong(subject);
    }

    public List<TagView> findAllTagsByUid(long uid) {
        return tagDao.findAllByUid(uid).stream()
                .map(it -> {
                    TagView view = BeanCopierUtil.copy(it, TagView.class);
                    long count = postDao.countByUidAndTid(uid, it.getId());
                    view.setCount(count);
                    return view;
                }).collect(Collectors.toList());
    }

    public void fillTags(PostView view) {
        List<Long> tids = postTagDao.findAllByPid(view.getId()).stream()
                .map(PostTagEntity::getTid).collect(Collectors.toList());

        List<TagView> tags = tagDao.findAllById(tids).stream()
                .map(it -> BeanCopierUtil.copy(it, TagView.class))
                .collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(tags)) {
            tags.sort(Comparator.comparing(TagView::getName));
            view.setTags(tags);
        } else {
            view.setTags(Collections.emptyList());
        }
    }

}
