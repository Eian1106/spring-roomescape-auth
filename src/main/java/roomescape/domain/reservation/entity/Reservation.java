package roomescape.domain.reservation.entity;

import roomescape.domain.theme.entity.Theme;
import roomescape.domain.time.entity.ReservationTime;

import java.time.LocalDate;

public class Reservation {

    private Long id;

    private final String username;

    private final Long userId;

    private final Long storeId;

    private final Theme theme;

    private final LocalDate date;

    private final ReservationTime time;

    public Reservation(Long id, String username, Theme theme, LocalDate date, ReservationTime time) {
        this(id, username, null, 1L, theme, date, time);
    }

    public Reservation(Long id, String username, Long userId, Theme theme, LocalDate date, ReservationTime time) {
        this(id, username, userId, 1L, theme, date, time);
    }

    public Reservation(Long id, String username, Long userId, Long storeId, Theme theme, LocalDate date, ReservationTime time) {
        this.id = id;
        this.username = username;
        this.userId = userId;
        this.storeId = storeId;
        this.theme = theme;
        this.date = date;
        this.time = time;
    }

    public Reservation(String username, Theme theme, LocalDate date, ReservationTime time) {
        this(null, username, null, theme, date, time);
    }

    public Reservation(String username, Long userId, Theme theme, LocalDate date, ReservationTime time) {
        this(null, username, userId, 1L, theme, date, time);
    }

    public Reservation(String username, Long userId, Long storeId, Theme theme, LocalDate date, ReservationTime time) {
        this(null, username, userId, storeId, theme, date, time);
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

    public Long getStoreId() {
        return storeId;
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
