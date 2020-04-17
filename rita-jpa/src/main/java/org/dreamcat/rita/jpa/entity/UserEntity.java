package org.dreamcat.rita.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Create by tuke on 2020/2/6
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {
    private String name;
    private String password;
    private String email;

    private String firstName;
    private String lastName;
    private String style;
    // yyyy-MM-dd
    private String birthday;
    private Integer gender;
    //@Convert(converter=Gender.EnumConverter.class)
    //private Gender gender;
}
