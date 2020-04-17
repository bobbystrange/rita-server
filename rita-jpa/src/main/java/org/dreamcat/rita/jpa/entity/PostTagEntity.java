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
@Table(name = "post_tag")
public class PostTagEntity extends BaseEntity {
    private Long uid;
    private Long pid;
    private Long tid;
}
