package org.dreamcat.rita.controller.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dreamcat.rita.core.NonceQuery;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Create by tuke on 2020/2/26
 */
@Setter
@Getter
@ToString
public class RegisterQuery extends NonceQuery {
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_-]{3,31}$")
    private String username;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*-_+=/\\\\|:;\"',.?]{4,20}$")
    private String password;
    @Email
    private String email;
    @NotEmpty
    private String imageCode;

    @NotEmpty
    @Pattern(regexp = "^https?://[0-9a-z.-]+?(:[0-9]+?)?/[0-9a-z.-/]*?$")
    private String redirect;
    @Pattern(regexp = "^.{0,127}$")
    private String firstName;
    @Pattern(regexp = "^.{0,127}$")
    private String lastName;
}
