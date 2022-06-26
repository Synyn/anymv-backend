package com.anymv.entity.conveter;

import com.anymv.entity.Type;

import javax.persistence.AttributeConverter;

public class EnumTypeConverter implements AttributeConverter<Type, String> {
    @Override
    public String convertToDatabaseColumn(Type type) {
        if(type == null) {
            return null;
        }

        return type.getValue();
    }

    @Override
    public Type convertToEntityAttribute(String value) {
        if(value == null) {
            return null;
        }

        return Type.getType(value);
    }
}
