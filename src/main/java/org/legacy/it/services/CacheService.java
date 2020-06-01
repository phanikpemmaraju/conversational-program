package org.legacy.it.services;

import org.legacy.it.cache.ProgramCache;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

@Service
public class CacheService {

    @Cacheable(value = "programCache", key = "'cics1-'+#programName")
    public ProgramCache getProgramCache(String programName) {
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        return ProgramCache.builder()
                .executorService(executorService)
                .requestQueue(new LinkedBlockingDeque())
                .responseQueue(new SynchronousQueue())
                .build();
    }

    @CachePut(value = "programCache", key = "'cics1-'+#programName")
    public ProgramCache updateProgramCache(String programName, ProgramCache programCache){
        programCache.setRunning(true);
        return programCache;
    }
}
