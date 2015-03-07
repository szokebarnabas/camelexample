package com.bs.consumer.config;

import com.bs.consumer.infrastructure.adapters.primary.integration.camel.MyRouteBuilder;
import com.bs.messaging.infrastrucutre.JsonMessageSerializer;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.spring.Main;
import org.apache.camel.spring.SpringCamelContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

import javax.jms.ConnectionFactory;

@Configuration
@ComponentScan("com.bs.consumer")
public class CamelConfig {

    @Autowired
    private MyRouteBuilder routeBuilder;

    @Autowired
    private ApplicationContext springContext;

    @Value("${jms.broker.url}")
    private String brokerURL;

    @Value("${jms.broker")
    private String brokerName;

    @Bean
    public CamelContext createCamelContext(ActiveMQComponent activeMQComponent) throws Exception {
        CamelContext camelContext = new SpringCamelContext();
        camelContext.addRoutes(routeBuilder);
        camelContext.addComponent(brokerName, activeMQComponent);
        return camelContext;
    }

    @Bean
    public ProducerTemplate producerTemplate(CamelContext camelContext) {
        return camelContext.createProducerTemplate();
    }

    @Bean
    public Main camelMain() throws Exception {
        Main main = new Main();
        main.enableHangupSupport();
        main.setApplicationContext((AbstractApplicationContext) springContext);
        return main;
    }

    @Bean
    public ActiveMQConnectionFactory coreConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerURL);
        connectionFactory.setUseCompression(true);
        return connectionFactory;
    }

    @Bean
    public JmsConfiguration coreJmsConfig(ActiveMQConnectionFactory coreConnectionFactory) {
        JmsConfiguration jmsConfiguration = new JmsConfiguration();
        ConnectionFactory connectionFactory = new PooledConnectionFactory(coreConnectionFactory);
        jmsConfiguration.setConnectionFactory(connectionFactory);
        return jmsConfiguration;
    }

    @Bean
    public ActiveMQComponent coreActivemq(JmsConfiguration coreJmsConfig) {
        ActiveMQComponent component = new ActiveMQComponent();
        component.setConfiguration(coreJmsConfig);
        return component;
    }

    @Bean
    public JsonMessageSerializer jsonMessageSerializer() {
        return new JsonMessageSerializer();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}