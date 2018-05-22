package com.seabig.annotation.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YJZ
 *         date 2018/5/9
 *         description 自定义注解
 */

// 定义被保留的时间长短，该表示编译时注解
@Retention (RetentionPolicy.CLASS)
// 定义所修饰的对象范围，该表示注解范围为类成员（构造方法、方法、成员变量）
@Target (ElementType.FIELD)
public @interface BindView {
    int value();
}
