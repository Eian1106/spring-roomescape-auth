package roomescape.domain.user.request;

public record UserLoginRequest(
        String email,
        String password
) {
}
