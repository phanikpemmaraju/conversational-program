package org.legacy.it.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableCaching
public class CachingConfig {

//    @Bean
//    public CacheManager cacheManager() {
//        final SimpleCacheManager cacheManager = new SimpleCacheManager();
//        cacheManager.setCaches(asList(
//                new ConcurrentMapCache("executorServices"),
//                new ConcurrentMapCache("blockingQueues"),
//                new ConcurrentMapCache("program")));
//        return cacheManager;
//    }

//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        return null;
//    }


//    @Bean
//    public CacheManager cacheManager() {
//        return new EhCacheCacheManager(cacheManagerFactory().getObject());
//    }
//
//    @Bean
//    public EhCacheManagerFactoryBean cacheManagerFactory() {
//        final EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
//        cacheManagerFactoryBean.setShared(true);
//        return cacheManagerFactoryBean;
//    }
}
