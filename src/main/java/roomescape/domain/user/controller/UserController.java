package roomescape.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.common.auth.TokenProvider;
import roomescape.domain.user.entity.User;
import roomescape.domain.user.request.UserLoginRequest;
import roomescape.domain.user.request.UserRegisterRequest;
import roomescape.domain.user.response.UserLoginResponse;
import roomescape.domain.user.service.UserService;

@RestController
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    public UserController(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserRegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        User user = userService.login(request);
        String accessToken = tokenProvider.createToken(user.getId());

        return ResponseEntity.ok(new UserLoginResponse(accessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }
}
