package org.dreamcat.rita.view;

import lombok.Getter;
import lombok.Setter;

/**
 * Create by tuke on 2020/2/23
 */
@Getter
@Setter
public class PostFatView extends PostView {
    private PostThinView prev;
    private PostThinView next;
}
