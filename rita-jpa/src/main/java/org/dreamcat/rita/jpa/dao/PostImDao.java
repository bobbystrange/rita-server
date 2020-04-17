package org.dreamcat.rita.jpa.dao;

import org.dreamcat.rita.controller.face.FaceImView;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by tuke on 2020/3/13
 */
public interface PostImDao {
    List<FaceImView> findImsByUid(long uid, Pageable pageable);

    List<FaceImView> findImsByUidAndTid(long uid, long tid, Pageable pageable);

    @Repository
    class PostImDaoImpl implements PostImDao {

        private JdbcTemplate jdbcTemplate;

        public PostImDaoImpl(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public List<FaceImView> findImsByUid(long uid, Pageable pageable) {
            return jdbcTemplate.query("select id, mtime from post where uid = ? order by id desc limit ? offset ?",
                    new Object[]{uid, pageable.getPageSize(), pageable.getOffset()},
                    (rs, rowNum) -> new FaceImView(rs.getLong(1), rs.getLong(2)));
        }

        @Override
        public List<FaceImView> findImsByUidAndTid(long uid, long tid, Pageable pageable) {
            return jdbcTemplate.query("select distinct p.id, p.mtime " +
                            "from post p inner join post_tag pt on p.id = pt.pid " +
                            "where p.uid = ? and pt.tid = ? order by p.id desc limit ? offset ?",
                    new Object[]{uid, tid, pageable.getPageSize(), pageable.getOffset()},
                    (rs, rowNum) -> new FaceImView(rs.getLong(1), rs.getLong(2)));
        }
    }
}
