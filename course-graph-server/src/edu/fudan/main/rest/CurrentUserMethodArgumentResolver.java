package edu.fudan.main.rest;

import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.config.Constants;
import edu.fudan.main.domain.User;
import edu.fudan.main.exception.UserNotFoundException;
import edu.fudan.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private UserRepository userRepository;

    @Autowired
    public CurrentUserMethodArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // If parameter is User type and has @CurrentUser annotation, return true
        return parameter.getParameterType().isAssignableFrom(User.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        // Get current user id that we added when do the authorization checking
        Long currentUserId = (Long) webRequest.getAttribute(
                Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (currentUserId == null) {
            // Does not have this attribute, in other words, not createToken
            return null;
        } else {
            // Get from repository
            return userRepository.findById(currentUserId).orElseThrow(
                    () -> new UserNotFoundException(currentUserId)
            );
        }

    }
}
