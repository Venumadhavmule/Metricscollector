package com.venu.Metricscollector.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.venu.Metricscollector.model.MetricRecord;
import com.venu.Metricscollector.repository.MetricRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataCollectorService {

    private final MetricRepository metricRepository;
    private final HttpClient client = HttpClient.newHttpClient();

    @Value("${datadog.api-url}")
    private String datadogUrl;

    @Value("${datadog.api-key}")
    private String datadogApiKey;
    
    @Value("${datadog.application-key}")
    private String datadogAppApiKey;

    @Value("${prometheus.api-url}")
    private String prometheusUrl;

    @Value("${prometheus.query}")
    private String prometheusQuery;

    @Scheduled(fixedRate = 60000)
    public void collectData() {
        collectFromDatadog();
        collectFromPrometheus();
    }

    private void collectFromDatadog() {
        try {
            long to = Instant.now().getEpochSecond();
            long from = to - 3600;
            
            String metricQuery = URLEncoder.encode("system.cpu.idle{*}", StandardCharsets.UTF_8);
            String query = String.format("%s?from=%d&to=%d&query=%s", datadogUrl, from, to, metricQuery);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(query))
                    .header("DD-API-KEY", datadogApiKey)
                    .header("DD-APPLICATION-KEY", datadogAppApiKey)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("Datadog Status Code: {}", response.statusCode());
            log.info("Datadog Response: {}", response.body());

            if (response.statusCode() == 403) {
                log.error("Forbidden - Check API and App keys");
                return;
            }

            MetricRecord record = MetricRecord.builder()
                    .source("DATADOG")
                    .metricName("system.cpu.idle")
                    .rawData(response.body())
                    .timestamp(LocalDateTime.now())
                    .build();

            metricRepository.save(record);

        } catch (Exception e) {
            log.error("Error collecting from Datadog", e);
        }
    }


    private void collectFromPrometheus() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(prometheusUrl + "?query=" + prometheusQuery))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            log.info("Prometheus Response: {}", body);

            MetricRecord record = MetricRecord.builder()
                    .source("PROMETHEUS")
                    .metricName(prometheusQuery)
                    .rawData(body)
                    .timestamp(LocalDateTime.now())
                    .build();

            metricRepository.save(record);

        } catch (Exception e) {
            log.error("Error collecting from Prometheus", e); 
        }
    }
}
