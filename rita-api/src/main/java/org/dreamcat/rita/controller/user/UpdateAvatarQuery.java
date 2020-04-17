package org.dreamcat.rita.controller.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Create by tuke on 2020/3/8
 */
@Data
public class UpdateAvatarQuery {
    @NotEmpty
    private String avatar;
}
