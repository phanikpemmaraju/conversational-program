package org.legacy.it.api;

import lombok.extern.slf4j.Slf4j;
import org.legacy.it.request.ServiceTask;
import org.legacy.it.services.CicsExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@Slf4j
public class ConversationServiceResource {

   @Autowired
   private CicsExecutorService cicsExecutorService;

   @RequestMapping(value = "/conversation-services", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   @Valid
   public ServiceTask conversationalService(@RequestBody @Valid ServiceTask serviceTask) {
      System.out.println("Executor Service: {} " + cicsExecutorService.getExecutorService());
      System.out.println("Blocking Queue: {} " + cicsExecutorService.getBlockingQueue());
      return cicsExecutorService.nextStage(serviceTask);
   }
}
