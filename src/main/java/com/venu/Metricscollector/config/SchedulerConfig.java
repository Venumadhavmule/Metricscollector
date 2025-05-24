package com.venu.Metricscollector.config;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.venu.Metricscollector.service.DataCollectorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SchedulerConfig {
	
	private final DataCollectorService dataCollectorService;

    
    @Scheduled(fixedRate = 6000)
    public void runCollectorTask() {
        log.info("Running scheduled data collection...");
        try {
            dataCollectorService.collectData();
        } catch (Exception e) {
            log.error("Scheduled data collection failed", e);
        }
    }
}