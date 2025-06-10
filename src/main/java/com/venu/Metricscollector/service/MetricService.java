package com.venu.Metricscollector.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.venu.Metricscollector.repository.MetricLogRepository;
import com.venu.Metricscollector.model.MetricLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricService {

    @Autowired
    private MetricLogRepository metricRepository;

    public List<MetricLog> saveAllMetrics(List<MetricLog> metrics) {
        return metricRepository.saveAll(metrics);
    }

    public List<MetricLog> getAllMetrics() {
        return metricRepository.findAll();
    }

    public List<MetricLog> getBySource(String source) {
        return metricRepository.findBySource(source);
    }

    public List<MetricLog> getByLevel(String level) {
        return metricRepository.findByLevel(level);
    }

    public List<MetricLog> getByMessage(String message) {
        return metricRepository.findByMessageContainingIgnoreCase(message);
    }

    public List<MetricLog> getByTimestamp(Long timestamp) {
        return metricRepository.findByTimestamp(timestamp);
    }

    public List<MetricLog> getByTimestampRange(Long start, Long end) {
        return metricRepository.findByTimestampBetween(start, end);
    }

    public List<MetricLog> getByStartTimestamp(Long start) {
        return metricRepository.findByTimestampGreaterThanEqual(start);
    }

    public List<MetricLog> getByEndTimestamp(Long end) {
        return metricRepository.findByTimestampLessThanEqual(end);
    }

	public List<MetricLog> findByType(String type) {
		return metricRepository.findByType(type);
	}
}
