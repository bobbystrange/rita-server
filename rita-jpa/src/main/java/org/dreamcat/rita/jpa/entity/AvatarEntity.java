package org.dreamcat.rita.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Create by tuke on 2020/3/10
 */
@Getter
@Setter
@Entity
@Table(name = "avatar")
public class AvatarEntity extends BaseEntity {
    private Long uid;
    private String avatar;
}
