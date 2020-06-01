package org.legacy.it.services;

import lombok.extern.slf4j.Slf4j;
import org.legacy.it.cache.ProgramCache;
import org.legacy.it.program.Cics;
import org.legacy.it.request.ServiceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
        log.info("Executor Service: {} " , programCache.getExecutorService());
        log.info("Blocking Queue: {} " , programCache.getRequestQueue());
        ServiceTask responseServiceTask;
        Cics cics = new Cics(serviceTask.getProgramName() + "-session", programCache.getRequestQueue(), programCache.getResponseQueue());
        if (!programCache.isRunning()) {
            programCache.getExecutorService().submit(cics);
            cacheService.updateProgramCache(programName, programCache);
        }
        try{
            programCache.getRequestQueue().put(serviceTask);
        } catch (Exception e){e.printStackTrace();}

        synchronized (programCache.getResponseQueue()) {
            try{
                responseServiceTask = (ServiceTask) programCache.getResponseQueue().poll(5000, TimeUnit.SECONDS);
                if (Objects.nonNull(responseServiceTask)) {
                    log.info("Response Service Task Screen Name: {} " , responseServiceTask.getName());
                }
            } catch (Exception e){return null;}
        }
        return responseServiceTask;
    }
}
