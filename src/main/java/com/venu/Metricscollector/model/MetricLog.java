package com.venu.Metricscollector.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "metrics_logs")
@AllArgsConstructor
@NoArgsConstructor
public class MetricLog {

    @Id
    private String id;

    private String source;   
    private String type;
    private String level;
    private String message;
    private long timestamp;
}
