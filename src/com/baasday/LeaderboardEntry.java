package com.baasday;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardEntry extends BaasdayObject {
    private final String leaderboardName;

    public LeaderboardEntry(final String leaderboardName, final Map<String, Object> values) {
        super(values);
        this.leaderboardName = leaderboardName;
    }

    public int getScore() throws BaasdayException {
        return this.getInt("_score");
    }

    public int getRank() throws BaasdayException {
        return this.getInt("_rank");
    }

    public int getOrder() throws BaasdayException {
        return this.getInt("_order");
    }

    private static String leaderboardAPIPath(final String leaderboardName) {
        return "leaderboards/" + leaderboardName;
    }

    private static String apiPath(final String leaderboardName, final String id) {
        return leaderboardAPIPath(leaderboardName) + "/" + id;
    }

    String apiPath() throws BaasdayException {
        return apiPath(this.leaderboardName, this.getId());
    }

    private static class LeaderboardEntryFactory implements APIClient.BaasdayObjectFactory<LeaderboardEntry> {
        private final String leaderboardName;

        LeaderboardEntryFactory(final String leaderboardName) {
            this.leaderboardName = leaderboardName;
        }

        public LeaderboardEntry createFromAPIResult(final Map<String, Object> values) throws BaasdayException {
            return new LeaderboardEntry(this.leaderboardName, values);
        }
    }

    public static LeaderboardEntry create(final String leaderboardName, final Map<String, Object> values) throws BaasdayException {
        return APIClient.create(leaderboardAPIPath(leaderboardName), values, new LeaderboardEntryFactory(leaderboardName));
    }

    public static LeaderboardEntry create(final String leaderboardName, final int score, final Map<String, Object> values) throws BaasdayException {
        final Map<String, Object> mergedValues = values == null ? new HashMap<String, Object>() : new HashMap<String, Object>(values);
        mergedValues.put("_score", score);
        return create(leaderboardName, mergedValues);
    }

    public static LeaderboardEntry create(final String leaderboardName, final int score) throws BaasdayException {
        return create(leaderboardName, score, null);
    }

    public static LeaderboardEntry fetch(final String leaderboardName, final String id) throws BaasdayException {
        return APIClient.fetch(apiPath(leaderboardName, id), new LeaderboardEntryFactory(leaderboardName));
    }

    public static ListResult<LeaderboardEntry> fetchAll(final String leaderboardName, final Query query) throws BaasdayException {
        return APIClient.fetchAll(leaderboardAPIPath(leaderboardName), query, new LeaderboardEntryFactory(leaderboardName));
    }

    public static ListResult<LeaderboardEntry> fetchAll(final String leaderboardName) throws BaasdayException {
        return fetchAll(leaderboardName, null);
    }
}
