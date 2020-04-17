package org.dreamcat.rita.jpa.dao;

import org.dreamcat.rita.jpa.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by tuke on 2020/3/10
 */
public interface TagDao extends JpaRepository<TagEntity, Long> {

    List<TagEntity> findAllByUid(long uid);

    long countAllByUid(long uid);

    TagEntity findByUidAndName(long uid, String name);
}
