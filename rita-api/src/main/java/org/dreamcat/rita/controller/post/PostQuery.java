package org.dreamcat.rita.controller.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Create by tuke on 2020/3/1
 */
@Setter
@Getter
@ToString
public class PostQuery {
    @Size(min = 4, max = 128)
    private String title;

    private String style;

    @Size(min = 4, max = 128)
    private String name;

    private boolean published;

    private boolean favorite;

    @Size(max = 1024)
    private String summary;

    @Size(max = 32)
    private List<String> tags;

    @Size(min = 64, max = 65536)
    private String content;
}
