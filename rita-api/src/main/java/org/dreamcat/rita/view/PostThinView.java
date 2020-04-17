package org.dreamcat.rita.view;

import lombok.Getter;
import lombok.Setter;

/**
 * Create by tuke on 2020/3/2
 */
@Getter
@Setter
public class PostThinView {
    private long id;
    private String name;
    private String title;
    private long ctime;
    private long mtime;
    private boolean published;
    private boolean favorite;
}
