package com.venu.Metricscollector.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.venu.Metricscollector.model.MetricRecord;

@Repository
public interface MetricRepository extends JpaRepository<MetricRecord, Long> ,JpaSpecificationExecutor<MetricRecord> {
	
}
