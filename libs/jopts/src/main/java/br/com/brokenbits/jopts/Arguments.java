package br.com.brokenbits.jopts;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows the use of multiple argument definitions for a single method (aliases).
 * 
 * @author FJTC
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Arguments {
	
	/**
	 * The list of argument definitions.
	 */
	Argument[] arguments();
}
