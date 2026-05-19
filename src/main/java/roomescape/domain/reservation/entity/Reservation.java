package roomescape.domain.reservation.entity;

import roomescape.domain.theme.entity.Theme;
import roomescape.domain.time.entity.ReservationTime;

import java.time.LocalDate;

public class Reservation {

    private Long id;

    private final String username;

    private final Long userId;

    private final Theme theme;

    private final LocalDate date;

    private final ReservationTime time;

    public Reservation(Long id, String username, Theme theme, LocalDate date, ReservationTime time) {
        this(id, username, null, theme, date, time);
    }

    public Reservation(Long id, String username, Long userId, Theme theme, LocalDate date, ReservationTime time) {
        this.id = id;
        this.username = username;
        this.userId = userId;
        this.theme = theme;
        this.date = date;
        this.time = time;
    }

    public Reservation(String username, Theme theme, LocalDate date, ReservationTime time) {
        this(null, username, null, theme, date, time);
    }

    public Reservation(String username, Long userId, Theme theme, LocalDate date, ReservationTime time) {
        this(null, username, userId, theme, date, time);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    public Theme getTheme() {
        return theme;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public boolean isOwnedBy(String username) {
        return this.username.equals(username);
    }

    public boolean hasSameSchedule(LocalDate date, ReservationTime time) {
        return this.date.equals(date) && this.time.getId().equals(time.getId());
    }
}
