package roomescape.common.auth;

import roomescape.common.exception.BusinessException;
import roomescape.common.exception.ErrorCode;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}
