package org.dreamcat.rita.controller.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Create by tuke on 2020/3/4
 */
@Data
public class UpdateUserQuery {
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_-]{3,31}$")
    private String username;
    @Email
    private String email;
    @NotEmpty
    private String gender;
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}")
    private String birthday;
    @Size(min = 2, max = 32)
    private String style;
    @Size(min = 2, max = 64)
    private String firstName;
    @Size(min = 1, max = 64)
    private String lastName;
}
