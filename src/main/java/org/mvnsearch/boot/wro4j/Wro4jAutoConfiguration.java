package org.mvnsearch.boot.wro4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.isdc.wro.config.jmx.ConfigConstants;
import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.resource.locator.factory.ConfigurableLocatorFactory;
import ro.isdc.wro.model.resource.processor.factory.ConfigurableProcessorsFactory;
import ro.isdc.wro.model.resource.support.hash.ConfigurableHashStrategy;
import ro.isdc.wro.model.resource.support.naming.ConfigurableNamingStrategy;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties(Wro4jProperties.class)
public class Wro4jAutoConfiguration {

    @Autowired
    Wro4jProperties wro4jProperties;

    @Bean
    ConfigurableWroFilter wroFilter(WroManagerFactory wroManagerFactory) {
        ConfigurableWroFilter wroFilter = new ConfigurableWroFilter();
        wroFilter.setProperties(wroFilterProperties());
        wroFilter.setWroManagerFactory(wroManagerFactory);
        return wroFilter;
    }

    Properties wroFilterProperties() {
        Properties properties = new Properties();
        properties.put(ConfigConstants.debug.name(), String.valueOf(wro4jProperties.isDebug()));
        properties.put(ConfigConstants.disableCache.name(), String.valueOf(wro4jProperties.isDisableCache()));
        properties.put(ConfigConstants.cacheUpdatePeriod, String.valueOf(wro4jProperties.getCacheUpdatePeriod()));
        properties.put(ConfigConstants.resourceWatcherUpdatePeriod, String.valueOf(wro4jProperties.getResourceWatcherUpdatePeriod()));
        properties.put(ConfigConstants.cacheGzippedContent, String.valueOf(wro4jProperties.isCacheGzippedContent()));
        properties.put(ConfigConstants.parallelPreprocessing, String.valueOf(wro4jProperties.isParallelProcessing()));
        return properties;
    }

    @Bean
    FilterRegistrationBean wro4jFilterRegistration(ConfigurableWroFilter wroFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(wroFilter);
        filterRegistrationBean.addUrlPatterns(wro4jProperties.getUrlPattern());
        return filterRegistrationBean;
    }

    @ConditionalOnMissingBean(WroManagerFactory.class)
    @Bean
    WroManagerFactory fallbackWroManagerFactory() {
        return new SimpleWroManagerFactory(wroManagerFactoryProperties());
    }

    Properties wroManagerFactoryProperties() {
        Properties properties = new Properties();
        properties.put(ConfigurableLocatorFactory.PARAM_URI_LOCATORS, wro4jProperties.getUriLocators());
        properties.put(ConfigurableProcessorsFactory.PARAM_PRE_PROCESSORS, wro4jProperties.getPreProcessors());
        properties.put(ConfigurableProcessorsFactory.PARAM_POST_PROCESSORS, wro4jProperties.getPostProcessors());
        properties.put(ConfigurableNamingStrategy.KEY, wro4jProperties.getNamingStrategy());
        properties.put(ConfigurableHashStrategy.KEY, wro4jProperties.getHashStrategy());
        return properties;
    }

}
