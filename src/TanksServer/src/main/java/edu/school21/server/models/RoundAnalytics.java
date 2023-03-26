package edu.school21.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoundAnalytics {

    private String playerId;

    private Integer shotsCount;

    private Integer hitsCount;

}
