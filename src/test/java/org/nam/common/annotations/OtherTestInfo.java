package org.nam.common.annotations;

import org.nam.common.enums.AuthorType;
import org.nam.common.enums.CategoryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OtherTestInfo {

    // This is not a method
    public AuthorType[] author();

    // public String[] category();
    public CategoryType[] category();


}