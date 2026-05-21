package roomescape.domain.user.entity;

public class User {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final UserRole role;
    private final Long storeId;

    public User(Long id, String username, String email, String password) {
        this(id, username, email, password, UserRole.MANAGER, 1L);
    }

    public User(Long id, String username, String email, String password, UserRole role, Long storeId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.storeId = storeId;
    }

    public User(String username, String email, String password) {
        this(null, username, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public Long getStoreId() {
        return storeId;
    }

    public boolean canManageStore(Long storeId) {
        return role == UserRole.MANAGER && this.storeId != null && this.storeId.equals(storeId);
    }
}
