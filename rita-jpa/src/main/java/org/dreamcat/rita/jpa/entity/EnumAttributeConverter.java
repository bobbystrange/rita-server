package org.dreamcat.rita.jpa.entity;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * Create by tuke on 2020/3/13
 */
@SuppressWarnings("unchecked")
public class EnumAttributeConverter<E extends EnumAttribute<T>, T> implements AttributeConverter<EnumAttribute<T>, T> {
    private Method valuesMethod;

    public EnumAttributeConverter() {
        Class<E> clazz = (Class<E>) (((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments())[0];
        try {
            valuesMethod = clazz.getMethod("values");
        } catch (Exception e) {
            throw new RuntimeException("can't get values method from " + clazz);
        }
    }

    @Override
    public T convertToDatabaseColumn(EnumAttribute<T> attribute) {
        return attribute.getValue();
    }

    @Override
    public EnumAttribute<T> convertToEntityAttribute(T dbData) {
        try {
            // static method needn't obj parameter
            E[] values = (E[]) valuesMethod.invoke(null);
            for (E value : values) {
                if (value.getValue().equals(dbData)) {
                    return value;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("unknown dbData " + dbData);
    }
}
