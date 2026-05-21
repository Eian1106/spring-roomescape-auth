package roomescape.common.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    public LoginCheckInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!isAdminReservationRequest(request) && !HttpMethod.GET.matches(request.getMethod()) && !HttpMethod.POST.matches(request.getMethod())) {
            return true;
        }

        if (tokenProvider.extractUserId(request).isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    private boolean isAdminReservationRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/admin/reservations");
    }
}
