package application.metrics;

import application.datasource.DataSource;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

public class EmploymentMetrics {
    public static final Counter requests = Counter.build().name("employment_requests").help("Requests made to endpoints").labelNames("path").register();
    public static final Gauge statistics = Gauge.build().name("employment_statistics").help("Employment statistics").labelNames("statistic").register();

    public static void collectEmploymentStatistics() {
        statistics.labels("total_years").set(DataSource.getInstance().fetchDataset().size());

        // statistics.labels("most_voted_song").set(DataSource.getInstance().findMostVotedSong().getId());
        // statistics.labels("max_votes_song").set(DataSource.getInstance().findMostVotedSong().getRatingCount());

        // statistics.labels("least_voted_song").set(DataSource.getInstance().findLeastVotedSong().getId());
        // statistics.labels("min_votes_song").set(DataSource.getInstance().findLeastVotedSong().getRatingCount());

        // statistics.labels("best_rated_song").set(DataSource.getInstance().findBestRatedSong().getId());
        // statistics.labels("max_rating_song").set(DataSource.getInstance().findBestRatedSong().getRating());

        // statistics.labels("lowest_rated_song").set(DataSource.getInstance().findLowestRatedSong().getId());
        // statistics.labels("min_rating_song").set(DataSource.getInstance().findLowestRatedSong().getRating());
    }
}
