package top.glimpse.webguide.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static javafx.scene.AccessibleAction.SET_VALUE;

/**
 * Created by joyce on 16-8-17.
 */
@Repository
public class OtherJdbcRepository implements OtherRepository{


    private static final String GET_VALUE = "select v from kv where k like ?";
    private static final String SET_VALUE = "UPDATE kv set v = ? where k like ?";
    private JdbcOperations jdbcOperations;

    @Autowired
    public OtherJdbcRepository(JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }


    @Override
    public String getValue(String key) {
        return jdbcOperations.queryForObject(
                GET_VALUE,
                String.class,
                key);

    }

    @Override
    public void setValue(final String key, final String value) {
        jdbcOperations.update(SET_VALUE,
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, value);
                        ps.setString(2, key);
                    }
                });
    }

    @Override
    public void setKV(String key, String value) {

    }
}
