package application.metrics;

import application.datasource.DataSource;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

public class EmploymentMetrics {
    public static final Counter requests = Counter.build().name("employment_requests").help("Requests made to endpoints").labelNames("path").register();
    public static final Gauge statistics = Gauge.build().name("employment_statistics").help("Employment statistics").labelNames("statistic").register();

    public static void collectEmploymentStatistics() {
        // statistics.labels("total_years").set(DataSource.getInstance().fetchDataset().size());

        statistics.labels("biggest_labor_force_percent_year").set(DataSource.getInstance().findInfoBiggestLaborForcePercent().getYear());
        statistics.labels("smallest_labor_force_percent_year").set(DataSource.getInstance().findInfoSmallestLaborForcePercent().getYear());

        statistics.labels("biggest_employed_percent_year").set(DataSource.getInstance().findInfoBiggestEmployedPercent().getYear());
        statistics.labels("smallest_employed_percent_year").set(DataSource.getInstance().findInfoSmallestEmployedPercent().getYear());

        statistics.labels("biggest_unemployed_percent_year").set(DataSource.getInstance().findInfoBiggestUnemployedPercent().getYear());
        statistics.labels("smallest_unemployed_percent_year").set(DataSource.getInstance().findInfoSmallestUnemployedPercent().getYear());
    }
}
