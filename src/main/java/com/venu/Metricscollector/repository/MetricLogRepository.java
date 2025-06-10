package com.venu.Metricscollector.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.venu.Metricscollector.model.MetricLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MetricLogRepository extends MongoRepository<MetricLog, String> {
	List<MetricLog> findBySource(String source);
    List<MetricLog> findByLevel(String level);
    List<MetricLog> findByMessageContainingIgnoreCase(String message);
    List<MetricLog> findByType(String type);
    List<MetricLog> findByTimestamp(Long timestamp);
    List<MetricLog> findByTimestampBetween(Long start, Long end);
    List<MetricLog> findByTimestampGreaterThanEqual(Long start);
    List<MetricLog> findByTimestampLessThanEqual(Long end);
}
