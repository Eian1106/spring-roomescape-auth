package roomescape.common.auth;

import roomescape.common.exception.BusinessException;
import roomescape.common.exception.ErrorCode;

public class ForbiddenException extends BusinessException {

    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN);
    }
}
