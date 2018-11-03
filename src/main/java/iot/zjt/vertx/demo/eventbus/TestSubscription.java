package iot.zjt.vertx.demo.eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class TestSubscription {

	public static void main(String[] args) {
		
		Vertx vertx = Vertx.vertx();
		EventBus eb = vertx.eventBus();

		MessageConsumer<Integer> mc1 = eb.consumer("ADDRESS");
		mc1.handler(message -> {
			System.out.println("getmsg1 " + message.body());
		});

		MessageConsumer<String> mc2 = eb.consumer("ADDRESS");
		mc2.handler(message -> {
			System.out.println("getmsg2 " + message.body());
		});

		eb.publish("ADDRESS", "2345");
		
	}

}
