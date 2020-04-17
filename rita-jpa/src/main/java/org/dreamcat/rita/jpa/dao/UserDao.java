package org.dreamcat.rita.jpa.dao;

import org.dreamcat.rita.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by tuke on 2020/3/8
 */
public interface UserDao extends JpaRepository<UserEntity, Long> {

    UserEntity findByName(String name);

    List<UserEntity> findAllByEmail(String email);
}
