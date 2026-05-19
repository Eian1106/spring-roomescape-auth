package roomescape.domain.user.service;

import org.springframework.stereotype.Service;
import roomescape.common.auth.UnauthorizedException;
import roomescape.domain.user.entity.User;
import roomescape.domain.user.repository.UserRepository;
import roomescape.domain.user.request.UserLoginRequest;
import roomescape.domain.user.request.UserRegisterRequest;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(UserRegisterRequest request) {
        User user = new User(request.username(), request.email(), request.password());
        return userRepository.save(user);
    }

    public User login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(UnauthorizedException::new);

        if (!user.getPassword().equals(request.password())) {
            throw new UnauthorizedException();
        }

        return user;
    }
}
