package com.tgt.mkt.cam.entity.types;

import java.util.Properties;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import com.vladmihalcea.hibernate.type.array.internal.ArraySqlTypeDescriptor;
import com.vladmihalcea.hibernate.type.array.internal.StringArrayTypeDescriptor;

public class StringArrayType extends AbstractSingleColumnStandardBasicType<String[]>
        implements DynamicParameterizedType {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public StringArrayType() {
        super(ArraySqlTypeDescriptor.INSTANCE, StringArrayTypeDescriptor.INSTANCE);
    }

    public String getName() {
        return "StringArray";
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((StringArrayTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }
}
