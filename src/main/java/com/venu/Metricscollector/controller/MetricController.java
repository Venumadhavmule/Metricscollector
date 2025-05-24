package com.venu.Metricscollector.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.venu.Metricscollector.model.MetricRecord;
import com.venu.Metricscollector.repository.MetricRepository;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricController {

    private final MetricRepository metricRepository;
   
    @GetMapping
    public List<MetricRecord> getMetrics(
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String metricName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return metricRepository.findAll((root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (source != null) {
                predicate = cb.and(predicate, cb.equal(root.get("source"), source));
            }
            if (metricName != null) {
                predicate = cb.and(predicate, cb.equal(root.get("metricName"), metricName));
            }
            if (from != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("timestamp"), from));
            }
            if (to != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("timestamp"), to));
            }
            return predicate;
        });
    }
}
