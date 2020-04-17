package org.dreamcat.rita.jpa.entity;

import lombok.AllArgsConstructor;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.common.util.StringUtil;

import javax.persistence.Converter;

/**
 * Create by tuke on 2020/3/12
 */
@AllArgsConstructor
public enum Gender implements EnumAttribute<Integer> {
    UNKNOWN(0),
    ASEXUAL(1),
    FAMALE(2),
    MALE(3),
    BISEXUAL(4);

    private final int value;

    public static String format(Integer genderType) {
        if (genderType == null) return UNKNOWN.toString();
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equals(genderType)) return gender.toString();
        }
        return UNKNOWN.toString();
    }

    public static int from(String genderString) {
        if (ObjectUtil.isBlank(genderString)) return UNKNOWN.getValue();
        for (Gender gender : Gender.values()) {
            if (genderString.equalsIgnoreCase(gender.name())) return gender.getValue();
        }
        return UNKNOWN.getValue();
    }

    public String toString() {
        return StringUtil.capitalize(name());
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Converter
    public static class EnumConverter extends EnumAttributeConverter<Gender, Integer> {
    }
}
