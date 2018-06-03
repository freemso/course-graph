package java.annotation;

import java.interceptor.AuthorizationInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User this annotation on methods in controller.
 * It will check if the user is in login status.
 * If not, return 401 UNAUTHORIZED error
 * @see AuthorizationInterceptor
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
}
