package application.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Enumeration;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import application.metrics.EmploymentMetrics;
import io.prometheus.client.Collector.MetricFamilySamples;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;

@RestController
@CrossOrigin
@RequestMapping("/")
public class MetricsEndpoint {

  @RequestMapping(value = "/metrics", method = RequestMethod.GET, produces = TextFormat.CONTENT_TYPE_004)
  @ResponseBody
  public ResponseEntity<String> metrics() {
    System.out.println( new Date() + " - Get Metrics");

    String metrics = "";

    // generate statistics
    EmploymentMetrics.collectEmploymentStatistics();

    Enumeration<MetricFamilySamples> samples = CollectorRegistry.defaultRegistry.metricFamilySamples();

    //write samples in the correct format
    Writer writer = new StringWriter();
    try {
      TextFormat.write004(writer, samples);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    metrics = writer.toString();
    System.out.println( new Date() + " - Metrics:\r\n" + metrics);

    return new ResponseEntity<String>(metrics, HttpStatus.OK);
  }

}
