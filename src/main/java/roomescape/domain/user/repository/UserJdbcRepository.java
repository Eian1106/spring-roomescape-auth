package roomescape.domain.user.repository;

import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.user.entity.User;
import roomescape.domain.user.entity.UserRole;

@Repository
public class UserJdbcRepository implements UserRepository {

    private static final String FIND_USER_BY_ID_QUERY = """
            SELECT id, username, email, password, role, store_id
            FROM users
            WHERE id = :id
            """;

    private static final String FIND_USER_BY_EMAIL_QUERY = """
            SELECT id, username, email, password, role, store_id
            FROM users
            WHERE email = :email
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public UserJdbcRepository(
            NamedParameterJdbcTemplate jdbcTemplate,
            DataSource dataSource
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("role", user.getRole().name())
                .addValue("store_id", user.getStoreId());

        Long generatedId = simpleJdbcInsert.executeAndReturnKey(parameters)
                .longValue();

        return new User(
                generatedId,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getStoreId()
        );
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id", id);

            User user = jdbcTemplate.queryForObject(
                    FIND_USER_BY_ID_QUERY,
                    parameters,
                    userRowMapper()
            );

            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("email", email);

            User user = jdbcTemplate.queryForObject(
                    FIND_USER_BY_EMAIL_QUERY,
                    parameters,
                    userRowMapper()
            );

            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    private RowMapper<User> userRowMapper() {
        return (resultSet, rowNumber) -> new User(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                UserRole.valueOf(resultSet.getString("role")),
                resultSet.getObject("store_id", Long.class)
        );
    }
}
