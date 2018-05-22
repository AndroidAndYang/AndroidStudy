package com.seabig.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * @author YJZ
 *         date 2018/5/10
 *         description 保存字段相关信息
 */
class ViewBinding {

    private final String name;
    private final TypeName type;
    private final int value;

    private ViewBinding(String name, TypeName type, int value)
    {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    static ViewBinding createViewBind(String name, TypeName type, int value)
    {
        return new ViewBinding(name, type, value);
    }

    public String getName()
    {
        return name;
    }

    public TypeName getType()
    {
        return type;
    }

    public int getValue()
    {
        return value;
    }


    ClassName getRawType()
    {
        if (type instanceof ParameterizedTypeName)
        {
            return ((ParameterizedTypeName) type).rawType;
        }
        return (ClassName) type;
    }
}
