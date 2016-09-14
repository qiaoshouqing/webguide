package top.glimpse.webguide.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import top.glimpse.webguide.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Henvealf on 16-5-14.
 */
@Repository
public class UserJdbcRepository implements UserRepository{

    private static final String SELECT_USER = "select * from user where name = ? and password = ?";
    private static final String SELECT_USER_BY_ID = "select * from user where uid = ?";
    private static final String INSERT_USER = "insert into user(name, password, email, created_at) values (?, ?)";
    private static final String UPDATE_USER = "update user set password = ? where uid = ?";


    private JdbcOperations jdbcOperations;

    @Autowired
    public UserJdbcRepository(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public User login(User user) {
        try {
            return jdbcOperations.queryForObject(
                    SELECT_USER,
                    new UserRowMapper(),
                    user.getName(), user.getPassword());
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User signup(User user) {

        jdbcOperations.update(INSERT_USER,
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                new Timestamp(System.currentTimeMillis()).toString());
        
        return jdbcOperations.queryForObject(
                SELECT_USER,
                new UserRowMapper(),
                user.getEmail(), user.getPassword());
    }

    @Override
    public User getUser(int uid) {
        try {
            return jdbcOperations.queryForObject(
                    SELECT_USER_BY_ID,
                    new UserRowMapper(),
                    uid);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getPassword(int uid) {
        return getUser(uid).getPassword();
    }

    @Override
    public void setPassword(final User user) {
        jdbcOperations.update(UPDATE_USER,
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, user.getPassword());
                        ps.setInt(2, user.getUid());
                    }
                });
    }


    private static class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getInt("uid"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("avatar"),
                    rs.getString("created_at"));
        }
    }
}
