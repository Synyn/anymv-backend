package com.anymv.service;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class CronService {

    private Logger logger = LoggerFactory.getLogger(CronService.class);


//    @Scheduled(cron = "0 0 * * *")
    public void updateDatabaseDaily() {
        CloseableHttpClient client = HttpClients.createDefault();


    }
}
