package com.example.activemqbridge;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Router extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:queue:SW_AMQ_OUT").routeId("generate-route")
                .log("Sending to WMQ")
                .to("wmq:queue:SW_WMQ_IN")
                .log("Received in WMQ");

        from("wmq:queue:SW").routeId("gen-route")
                .log("Sending from WMQ")
                .to("activemq:queue:SW_AMQ")
                .log("Received in AMQ");
    }
}
