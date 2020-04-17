package org.dreamcat.rita.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Create by tuke on 2020/3/12
 */
@Setter
@Getter
@Entity
@Table(name = "deleted_post")
public class DeletedPostEntity extends BaseEntity {
    private long uid;
    private String name;
    private String title;
    private Boolean published;
    private Boolean favorite;
    private String style;
    private String summary;
    private String content;
}
