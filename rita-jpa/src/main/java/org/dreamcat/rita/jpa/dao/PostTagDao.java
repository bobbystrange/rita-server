package org.dreamcat.rita.jpa.dao;

import org.dreamcat.rita.jpa.entity.PostTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by tuke on 2020/3/10
 */
public interface PostTagDao extends JpaRepository<PostTagEntity, Long> {

    PostTagEntity findByPidAndTid(long pid, long tid);

    List<PostTagEntity> findAllByPid(long pid);

    List<PostTagEntity> findAllByTid(long tid);
}
