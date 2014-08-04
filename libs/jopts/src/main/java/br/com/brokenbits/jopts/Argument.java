package br.com.brokenbits.jopts;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to indicate that a method is an argument handler method. 
 * 
 * <p>This annotation can be used for non static methods with zero or one parameter
 * which type can be long, Long, double, Double and String.
 * 
 * @author fjtc
 * @since 2014.07.22
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Argument {
	
	/**
	 * Name of the parameter as it will appear in the command line (e.g.: "-version"). The default value
	 * indicates that it is the unnamed parameter. Notice that '--' is reserved and cannot be used as a
	 * parameter name.
	 */
	String name() default "";

	/**
	 * Sets a unique key used to make sure that this parameter cannot be more than once in a given
	 * command line. If two or more parameters share the same uniqueKey, only one of them will be
	 * allowed in a given command line.
	 */
	String uniqueKey() default "";
	
	/**
	 * The literal description of this argument. It is used when the resourceName does not point
	 * to a valid string.
	 */
	String description() default "";
	
	/**
	 * The name of the resource of the description of this argument. This allows the use of the
	 * Java internationalization features. 
	 */
	String resourceName() default "";
}
