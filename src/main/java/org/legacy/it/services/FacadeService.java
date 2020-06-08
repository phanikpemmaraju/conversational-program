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

/*
*  NOTE: This is the main Service which creates instance of the Cics programs
*        and pushes messages to the request blocking queues.
*        The service polls on the response blocking queues for any response within the transaction.
*
*/

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
        if (activeCount <= 0) {
            final Cics cics = new Cics(requestQueue, responseQueue);
            executorService.submit(cics);
        }

        try{
            requestQueue.put(serviceTask);
            responseServiceTask = (ServiceTask) responseQueue.poll(5000, TimeUnit.SECONDS);
            if (Objects.nonNull(responseServiceTask)) {
                log.info("Response Service Task Screen Name: {} " , responseServiceTask.getName());
            }

            // graceful shutdown
            /* if(responseServiceTask.getName().equalsIgnoreCase("end")) {
                executorService.shutdownNow();
                cacheService.evictProgramCache(programName);
            } */
        } catch (Exception e){return null;}
        return responseServiceTask;
    }
}
