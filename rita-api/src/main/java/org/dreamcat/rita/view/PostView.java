package org.dreamcat.rita.view;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Create by tuke on 2020/2/23
 */
@Getter
@Setter
public class PostView extends PostThinView {
    private List<TagView> tags;
    private String style;
    private String summary;
    private String content;
}
