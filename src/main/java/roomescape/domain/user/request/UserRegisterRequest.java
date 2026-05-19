package roomescape.domain.user.request;

public record UserRegisterRequest(
        String username,
        String email,
        String password
) {
}
