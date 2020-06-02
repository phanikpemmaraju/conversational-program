package org.legacy.it.services;

import lombok.extern.slf4j.Slf4j;
import org.legacy.it.cache.ProgramCache;
import org.legacy.it.program.Cics;
import org.legacy.it.request.ServiceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class FacadeService {

    @Lazy
    @Autowired
    private CacheService cacheService;

    public ServiceTask nextStage(ServiceTask serviceTask) {
        final String programName = serviceTask.getProgramName();
        final ProgramCache programCache = cacheService.getProgramCache(programName);
        final BlockingQueue requestQueue = programCache.getRequestQueue();
        final BlockingQueue responseQueue = programCache.getResponseQueue();
        final ExecutorService executorService = programCache.getExecutorService();

        log.info("Program Cache: {} " , programCache);
        ServiceTask responseServiceTask;
        int activeCount = ((ThreadPoolExecutor) executorService).getActiveCount();

        if(serviceTask.getName().equalsIgnoreCase("end")) {
            executorService.shutdown();
            cacheService.evictProgramCache(programName);
            return null;
        } else {
            if (activeCount <= 0) {
                final Cics cics = new Cics(serviceTask.getProgramName() + "-session", requestQueue, responseQueue);
                executorService.submit(cics);
            }

            try{
                requestQueue.put(serviceTask);
            } catch (Exception e){e.printStackTrace();}

            synchronized (responseQueue) {
                try{
                    responseServiceTask = (ServiceTask) responseQueue.poll(5000, TimeUnit.SECONDS);
                    if (Objects.nonNull(responseServiceTask)) {
                        log.info("Response Service Task Screen Name: {} " , responseServiceTask.getName());
                    }
                } catch (Exception e){return null;}
            }
            return responseServiceTask;
        }
    }
}
