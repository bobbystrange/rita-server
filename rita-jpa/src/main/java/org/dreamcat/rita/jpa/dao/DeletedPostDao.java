package org.dreamcat.rita.jpa.dao;

import org.dreamcat.rita.jpa.entity.DeletedPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Create by tuke on 2020/3/12
 */
public interface DeletedPostDao extends JpaRepository<DeletedPostEntity, Long> {

    @Query(value = "select * from deleted_post where uid = ?1 order by id desc limit ?3 offset ?2",
            nativeQuery = true)
    List<DeletedPostEntity> findAllByUid(long uid, long offset, int size);

    long countAllByUid(long uid);
}
