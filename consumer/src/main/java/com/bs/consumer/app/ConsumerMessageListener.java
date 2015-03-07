package com.bs.consumer.app;

import com.bs.messaging.app.HelloMessage;
import com.bs.messaging.app.ProductBoughtEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsumerMessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerMessageListener.class);

    public void handleHelloMessage(HelloMessage helloMessage) {
        LOG.debug("Received HelloMessage: {}", helloMessage.toString());
    }

    public void handleProductBoughtMessage(ProductBoughtEventMessage pm) {
        LOG.debug("Received ProductBoughtEventMessage: {}", pm.toString());
    }
}
