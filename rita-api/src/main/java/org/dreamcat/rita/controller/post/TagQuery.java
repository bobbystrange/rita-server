package org.dreamcat.rita.controller.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

/**
 * Create by tuke on 2020/3/12
 */
@Getter
@Setter
@ToString
public class TagQuery {
    @Pattern(regexp = "^[a-zA-Z0-9.-_@#$%&*^<>?;:|/\\\\!]{2,32}$")
    private String name;
    private boolean favorite;
}
