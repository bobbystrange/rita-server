package org.dreamcat.rita.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Create by tuke on 2020/3/2
 */
@Setter
@Getter
@ToString
public class TagView {
    private long id;
    private long ctime;
    private long mtime;

    private String name;
    private boolean favorite;
    private long count;
}
