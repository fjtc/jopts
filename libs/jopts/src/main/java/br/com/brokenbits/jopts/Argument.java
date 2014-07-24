package br.com.brokenbits.jopts;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to define an argument definition.
 * 
 * @author fjtc
 * @since 2014.07.22
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Argument {
	
	/**
	 * Name of the parameter, such as "-argument" or "" to set is as unnamed parameter.
	 */
	String name() default "";

	/**
	 * Sets a unique key. This value must be set only if the parameter is supposed to appear only once
	 * per command line. All arguments with the same unique key will be mutually exclusive.
	 */
	String uniqueKey() default "";
	
	/**
	 * The literal description of this argument. 
	 */
	String description() default "";
	
	/**
	 * The name of the resource of the description of this argument. 
	 */
	String resourceName() default "";
}
