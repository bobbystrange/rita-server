package org.dreamcat.rita.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Create by tuke on 2020/2/6
 */
@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity extends BaseEntity {
    private Long uid;
    private String name;
    private Boolean favorite;
}
