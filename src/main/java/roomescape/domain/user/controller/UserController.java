package roomescape.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.user.entity.User;
import roomescape.domain.user.request.UserLoginRequest;
import roomescape.domain.user.request.UserRegisterRequest;
import roomescape.domain.user.service.UserService;

@RestController
public class UserController {

    private static final String LOGIN_USER_ID = "loginUserId";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserRegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody UserLoginRequest request,
            HttpServletRequest servletRequest
    ) {
        User user = userService.login(request);
        HttpSession session = servletRequest.getSession();
        session.setAttribute(LOGIN_USER_ID, user.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.noContent().build();
    }
}
