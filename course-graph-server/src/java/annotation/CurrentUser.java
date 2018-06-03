package java.annotation;

import java.lang.annotation.*;
import java.resolvers.CurrentUserMethodArgumentResolver;

/**
 * User this annotation on parameter(User type to be exact) of methods in controller.
 * It will inject current user object.
 * @see CurrentUserMethodArgumentResolver
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CurrentUser {
}
