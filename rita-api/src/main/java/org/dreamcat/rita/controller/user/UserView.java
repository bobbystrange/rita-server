package org.dreamcat.rita.controller.user;

import lombok.Getter;
import lombok.Setter;
import org.dreamcat.rita.view.UserThinView;

/**
 * Create by tuke on 2020/2/27
 */
@Getter
@Setter
public class UserView extends UserThinView {
    private String username;
    private String email;
    private String gender;
    private String birthday;
    private long postCount;
    private long favoritePostCount;
}
