package roomescape.domain.reservation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.common.auth.LoginUser;
import roomescape.domain.reservation.request.ReservationCreateRequest;
import roomescape.domain.reservation.request.ReservationUpdateRequest;
import roomescape.domain.reservation.response.ReservationResponse;
import roomescape.domain.reservation.response.ReservationsResponse;
import roomescape.domain.reservation.service.ReservationService;
import roomescape.domain.user.entity.User;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<ReservationsResponse> findMine(@LoginUser User loginUser) {
        List<ReservationResponse> reservations = reservationService.findReservationsByUser(loginUser);
        return ResponseEntity.ok(new ReservationsResponse(reservations));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> save(
            @LoginUser User loginUser,
            @Valid @RequestBody ReservationCreateRequest request
    ) {
        ReservationResponse response = reservationService.saveReservation(loginUser, request);
        return ResponseEntity.created(URI.create("/reservations/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long reservationId,
            @RequestParam String username
    ) {
        reservationService.cancelReservationBy(reservationId, username);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> updateReservationSchedule(
            @PathVariable Long reservationId,
            @RequestParam String username,
            @Valid @RequestBody ReservationUpdateRequest request
    ) {
        ReservationResponse response = reservationService.updateReservationSchedule(
                reservationId,
                username,
                request
        );
        return ResponseEntity.ok(response);
    }
}
