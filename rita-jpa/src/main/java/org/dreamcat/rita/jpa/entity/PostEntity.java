package org.dreamcat.rita.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Create by tuke on 2020/2/3
 */
@Getter
@Setter
@Entity
@Table(name = "post")
public class PostEntity extends BaseEntity {
    private Long uid;
    private String name;
    private String title;
    private Boolean published;
    private Boolean favorite;
    private String style;
    private String summary;
    private String content;
}
