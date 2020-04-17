package org.dreamcat.rita.jpa.dao;

import org.dreamcat.rita.jpa.entity.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Create by tuke on 2020/3/10
 */
public interface PostDao extends JpaRepository<PostEntity, Long>, PostImDao {

    PostEntity findByUidAndName(long uid, String name);

    //@Query(value = "select id, mtime from PostEntity where uid = :uid order by id desc")
    //List<FaceImView> findImsByUid(long uid, Pageable pageable);

    long countByUid(long uid);

    //@Query(value = "select distinct p.id, p.mtime " +
    //        "from PostEntity p inner join PostTagEntity pt " +
    //        "on p.id = pt.pid " +
    //        "where p.uid = :uid and pt.tid = :tid order by p.id desc")
    //List<FaceImView> findImsByUidAndTid(long uid, long tid, Pageable pageable);

    @Query(value = "select count(distinct p.id) " +
            "from PostEntity p inner join PostTagEntity pt " +
            "on p.id = pt.pid " +
            "where p.uid = :uid and pt.tid = :tid")
    long countByUidAndTid(long uid, long tid);

    @Query(value = "select * from post where id < :id order by id limit 1 ", nativeQuery = true)
    PostEntity findPrevPost(long id);

    @Query(value = "select * from post where id > :id order by id limit 1 ", nativeQuery = true)
    PostEntity findNextPost(long id);

    @Query(value = "select p from PostEntity p where p.uid = :uid order by p.id desc")
    List<PostEntity> findAllByUid(long uid, Pageable pageable);

    @Query(value = "select p from PostEntity p where p.uid = :uid and p.favorite = true order by p.id desc")
    List<PostEntity> findFavoriteByUid(long uid, Pageable pageable);

    @Query(value = "select count(id) from PostEntity where uid = :uid and favorite = true")
    long countFavoriteByUid(long uid);
}
