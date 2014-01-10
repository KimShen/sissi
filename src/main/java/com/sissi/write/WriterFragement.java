package com.sissi.write;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kim 2014年1月10日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface WriterFragement {

	public WriterPart part() default WriterPart.WITHOUT_LAST;
}
