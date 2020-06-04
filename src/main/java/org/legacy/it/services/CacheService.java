package org.legacy.it.services;

import lombok.extern.slf4j.Slf4j;
import org.legacy.it.cache.ProgramCache;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class CacheService {

    @Lookup
    public ExecutorService getNewExecutorService() {
        return null;
    }

    @Cacheable(value = "programCache", key = "'cics1-'+#programName")
    public ProgramCache getProgramCache(String programName) {
        final ExecutorService executorService = getNewExecutorService();
        log.info("Program Caching Name:{}, executorService:{} ", programName, executorService);
        return ProgramCache.builder()
                .executorService(executorService)
                .requestQueue(new ArrayBlockingQueue(100))
                .responseQueue(new ArrayBlockingQueue(100))
                .build();
    }

    @CacheEvict(value = "programCache", key = "'cics1-'+#programName")
    public void evictProgramCache(String programName) {}

}
