package com.tsoft.ischool.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = {MetricsConfiguration.class})
@AutoConfigureBefore(value = {WebConfigurer.class, DatabaseConfiguration.class})
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache
                = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                        ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                        .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                        .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.tsoft.ischool.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.Role.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.Role.class.getName() + ".privileges", jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.User.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.Privilege.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.Professeur.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.Eleve.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.Employe.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.EmployeFonction.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.CompteAnalytiqueEcriture.class.getName(), jcacheConfiguration);
            cm.createCache(com.tsoft.ischool.domain.CompteAnalytique.class.getName(), jcacheConfiguration);

            // jhipster-needle-ehcache-add-entry
        };
    }
}
