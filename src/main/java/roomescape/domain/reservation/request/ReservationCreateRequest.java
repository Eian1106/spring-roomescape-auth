package roomescape.domain.reservation.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationCreateRequest(
        String username,

        @NotNull(message = "테마는 필수입니다.")
        Long themeId,

        @NotNull(message = "예약 날짜는 필수입니다.")
        LocalDate date,

        @NotNull(message = "예약 시간은 필수입니다.")
        Long timeId
) {
}
