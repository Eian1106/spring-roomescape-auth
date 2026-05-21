package roomescape.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import roomescape.common.auth.LoginCheckInterceptor;
import roomescape.common.auth.LoginUserArgumentResolver;
import roomescape.common.auth.TokenProvider;
import roomescape.domain.user.repository.UserRepository;

import java.util.List;

@Configuration
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public AuthenticationPrincipalConfig(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor(tokenProvider))
                .addPathPatterns("/reservations/**", "/admin/reservations/**")
                .excludePathPatterns(
                        "/login",
                        "/logout",
                        "/signup",
                        "/themes/popular"
                );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver(userRepository, tokenProvider));
    }
}
