package org.dreamcat.rita.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dreamcat.common.web.core.PageView;

/**
 * Create by tuke on 2020/3/2
 */
@Setter
@Getter
@ToString
public class TagFatView extends TagView {
    private PageView<PostThinView> posts;
}
