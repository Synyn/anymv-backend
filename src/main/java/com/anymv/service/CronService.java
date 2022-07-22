package com.anymv.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class CronService {

    private Logger logger = LoggerFactory.getLogger(CronService.class);

    @Autowired
    private AnimeOfflineDBService animeOfflineDBService;

    @Scheduled(cron = "0 44 08 * * *")
    public void updateDatabaseDaily() {
        animeOfflineDBService.updateDatabase();
    }
}
