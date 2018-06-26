package edu.fudan.rest;

import edu.fudan.Application;
import edu.fudan.annotation.Authorization;
import edu.fudan.domain.TokenEntry;
import edu.fudan.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Customized interceptor. Used to check if the request is authorized.
 *
 * @see Authorization
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private TokenRepository tokenRepository;

    @Autowired
    public AuthorizationInterceptor(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        // If not on method, just return true
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // Get authorization field from header
        String authorization = request.getHeader(Application.AUTHORIZATION);
        // Check the token
        TokenEntry tokenEntry = tokenRepository.getToken(authorization);
        if (tokenRepository.checkToken(tokenEntry)) {
            // Passed. Save current user id in request for future use
            request.setAttribute(Application.CURRENT_USER_ID, tokenEntry.getId());
            return true;
        } else {
            // Failed.
            // If the method has a @Authorization annotation, return a 401 UNAUTHORIZED error
            if (method.getAnnotation(Authorization.class) != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }
        return true;
    }
}
