package org.dreamcat.rita.core;

import lombok.Data;

/**
 * Create by tuke on 2020/3/11
 */
@Data
public class PasswordResetEntity {
    private String password;
    private long timestamp;
}
