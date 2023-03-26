package edu.school21.server.repositories;

import edu.school21.server.models.RoundAnalytics;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
public class RoundAnalyticsImpl implements RoundAnalyticsRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoundAnalyticsImpl(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute(
                "create table if not exists round_analytics\n" +
                        "(\n" +
                        "    player_id   text primary key not null,\n" +
                        "    shots_count integer          not null,\n" +
                        "    hits_count  integer          not null\n" +
                        ");");
    }

    @Override
    public void save(RoundAnalytics roundAnalytics) {
        int i = jdbcTemplate.update("insert into round_analytics (player_id, shots_count, hits_count)" +
                "values (?, ?, ?)",
                roundAnalytics.getPlayerId(),
                roundAnalytics.getShotsCount(),
                roundAnalytics.getHitsCount());
        if (i == 0) {
            System.err.println("Seems like analytics going to hell");
        }
    }

    @Override
    public void update(RoundAnalytics roundAnalytics) {
        int i = jdbcTemplate.update("update round_analytics set shots_count = ?, hits_count = ? " +
                        "where player_id = ?;",
                roundAnalytics.getShotsCount(),
                roundAnalytics.getHitsCount(),
                roundAnalytics.getPlayerId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.execute("truncate round_analytics");
    }

    @Override
    public RoundAnalytics findByPlayerId(String playerId) {
        String sql = "select * from round_analytics where player_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(RoundAnalytics.class), playerId);
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
