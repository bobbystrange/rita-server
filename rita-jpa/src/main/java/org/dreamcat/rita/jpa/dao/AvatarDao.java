package org.dreamcat.rita.jpa.dao;

import org.dreamcat.rita.jpa.entity.AvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by tuke on 2020/3/10
 */
public interface AvatarDao extends JpaRepository<AvatarEntity, Long> {

    AvatarEntity findByUid(long uid);
}
