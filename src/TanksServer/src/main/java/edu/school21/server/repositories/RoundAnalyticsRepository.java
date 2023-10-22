package edu.school21.server.repositories;

import edu.school21.server.models.RoundAnalytics;

import java.util.List;
import java.util.Optional;

public interface RoundAnalyticsRepository {

    void save(RoundAnalytics roundAnalytics);

    void update(RoundAnalytics roundAnalytics);

    void deleteAll();

    RoundAnalytics findByPlayerId(String playerId);

    List<RoundAnalytics> findAll();

}
