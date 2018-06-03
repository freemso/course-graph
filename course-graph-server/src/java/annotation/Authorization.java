package java.annotation;

import java.interceptor.AuthorizationInterceptor;

import java.lang.annotation.*;

/**
 * User this annotation on methods in controller.
 * It will check if the user is in login status.
 * If not, return 401 UNAUTHORIZED error
 * @see AuthorizationInterceptor
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Authorization {
}
