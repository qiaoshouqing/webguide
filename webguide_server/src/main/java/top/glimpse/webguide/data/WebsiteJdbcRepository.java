package top.glimpse.webguide.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import top.glimpse.webguide.entity.Website;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by joyce on 16-8-14.
 */
@Repository
public class WebsiteJdbcRepository implements WebsiteRepository {
    private static final String SELECT_WEBSITE_ALL = "select * from website order by weight desc";
    private static final String SELECT_WEBSITE_ONE = "select * from website where wid = ?";

    private static final String INSERT_WEBSITE = "insert into website(wname, logo, url, weight) values(?, ?, ?, ?);";
    private static final String UPDATE_WEBSITE = "update website set wname = ?, logo = ?, url = ?, weight = ? where wid = ?";
    private static final String DELETE_WEIGHT = "delete from website where wid = ?";


    private JdbcOperations jdbcOperations;

    @Autowired
    public WebsiteJdbcRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Website> getAll() {
        return jdbcOperations.query(
                SELECT_WEBSITE_ALL,
                new WebsiteRowMapper());
    }

    @Override
    public Website getOne(int wid) {
        try {
            return jdbcOperations.queryForObject(
                    SELECT_WEBSITE_ONE,
                    new WebsiteRowMapper(), wid);
        } catch (org.springframework.dao.EmptyResultDataAccessException e)
        {
            return null;
        }
    }

    @Override
    public void postOne(Website website) {

        jdbcOperations.update(INSERT_WEBSITE,
                website.getWname(),
                website.getLogo(),
                website.getUrl(),
                website.getWeight());

    }


    @Override
    public void updateOne(final Website website) {
        jdbcOperations.update(UPDATE_WEBSITE,
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, website.getWname());
                        ps.setString(2, website.getLogo());
                        ps.setString(3, website.getUrl());
                        ps.setInt(4, website.getWeight());
                        ps.setInt(5, website.getWid());
                    }
                });
    }

    @Override
    public void deleteOne(int wid) {
        jdbcOperations.update(DELETE_WEIGHT,
                wid);
    }


    private static class WebsiteRowMapper implements RowMapper<Website> {
        public Website mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Website(
                    rs.getInt("wid"),
                    rs.getString("wname"),
                    rs.getString("logo"),
                    rs.getString("url"),
                    rs.getInt("weight"));
        }
    }
}
