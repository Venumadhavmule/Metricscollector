package com.venu.Metricscollector.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "metric_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DATADOG or PROMETHEUS	
    private String source; 

    private String metricName;

    @Column(columnDefinition = "TEXT")
    private String rawData;

    private LocalDateTime timestamp;
}