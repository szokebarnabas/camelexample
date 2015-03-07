package com.bs.consumer.infrastructure.adapters.primary.integration.camel;

import com.bs.consumer.app.ConsumerMessageListener;
import com.bs.messaging.app.HelloMessage;
import com.bs.messaging.app.ProductBoughtEventMessage;
import com.bs.messaging.infrastrucutre.JsonMessageSerializer;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyRouteBuilder extends RouteBuilder {

    private static final String IN_QUEUE = "hello.in";
    private static final String MESSAGE_TYPE = "MessageType";

    @Value("coreActivemq")
    private String jmsBroker;

    @Autowired
    private JsonMessageSerializer serializer;

    @Autowired
    private ConsumerMessageListener messageHandlerApplicationService;

    @Override
    public void configure() throws Exception {
        from(jmsBroker + ":" + IN_QUEUE)
                .to("log:jmsRootLog?showAll=true")
                .choice()
                    .when(header(MESSAGE_TYPE).isEqualTo(HelloMessage.class.getName()))
                        .unmarshal().json(JsonLibrary.Gson, HelloMessage.class)
                        .bean(messageHandlerApplicationService, "handleHelloMessage")
                    .when(header(MESSAGE_TYPE).isEqualTo(ProductBoughtEventMessage.class.getName()))
                        .unmarshal().json(JsonLibrary.Gson, ProductBoughtEventMessage.class)
                        .bean(messageHandlerApplicationService, "handleProductBoughtMessage");
    }
}
