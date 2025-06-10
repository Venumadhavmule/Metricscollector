package com.venu.Metricscollector.controller;

import com.venu.Metricscollector.model.MetricLog;
import com.venu.Metricscollector.service.MetricService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/metrics")
public class MetricLogController {

    private final MetricService metricService;

    public MetricLogController(MetricService metricService) {
        this.metricService = metricService;
    }

    @PostMapping
    public ResponseEntity<List<MetricLog>> saveMetrics(@RequestBody List<MetricLog> metrics) {
        long timestamp = System.currentTimeMillis();
        for (MetricLog metric : metrics) {
            metric.setTimestamp(timestamp);
        }
        List<MetricLog> savedMetrics = metricService.saveAllMetrics(metrics);
        log.info("Saved Metrics: {}", savedMetrics);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMetrics);
    }

    @GetMapping
    public ResponseEntity<List<MetricLog>> getAll() {
        return ResponseEntity.ok(metricService.getAllMetrics());
    }

    @GetMapping("/source/{source}")
    public ResponseEntity<List<MetricLog>> getBySource(@PathVariable String source) {
        return ResponseEntity.ok(metricService.getBySource(source));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<MetricLog>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(metricService.findByType(type));
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<MetricLog>> getByLevel(@PathVariable String level) {
        return ResponseEntity.ok(metricService.getByLevel(level));
    }

    @GetMapping("/by-timestamp")
    public ResponseEntity<List<MetricLog>> getMetricsByTimestampRange(
            @RequestParam(required = false) Long start,
            @RequestParam(required = false) Long end) {

        List<MetricLog> results;

        if (start != null && end != null) {
            results = metricService.getByTimestampRange(start, end);
        } else if (start != null) {
            results = metricService.getByStartTimestamp(start);
        } else if (end != null) {
            results = metricService.getByEndTimestamp(end);
        } else {
            results = metricService.getAllMetrics();
        }

        return ResponseEntity.ok(results);
    }
}
