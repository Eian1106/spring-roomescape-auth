package roomescape.common.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import roomescape.domain.user.entity.User;
import roomescape.domain.user.repository.UserRepository;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public LoginUserArgumentResolver(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
        boolean isUserType = User.class.isAssignableFrom(parameter.getParameterType());

        return hasAnnotation && isUserType;
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Long userId = tokenProvider.extractUserId(request)
                .orElseThrow(UnauthorizedException::new);

        return userRepository.findById(userId)
                .orElseThrow(UnauthorizedException::new);
    }
}
