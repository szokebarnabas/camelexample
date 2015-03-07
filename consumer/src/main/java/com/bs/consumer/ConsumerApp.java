package com.bs.consumer;

import com.bs.consumer.config.CamelConfig;
import com.bs.consumer.config.PropertiesConfig;
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({CamelConfig.class, PropertiesConfig.class})
public class ConsumerApp {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerApp.class);

    public static void main(String[] args) throws Exception {
        LOG.info("Starting the consumer...");
        ApplicationContext springContext = new SpringApplicationBuilder(ConsumerApp.class)
                .showBanner(false)
                .run(args);
        LOG.info("Consumer has been started");
        springContext.getBean(Main.class).run();
    }
}