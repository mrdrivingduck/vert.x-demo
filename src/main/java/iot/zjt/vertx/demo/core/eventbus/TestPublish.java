package iot.zjt.vertx.demo.core.eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class TestPublish {

	public static void main(String[] args) {
		
		Vertx vertx = Vertx.vertx();
		EventBus eb = vertx.eventBus();

		eb.consumer("ADDRESS", message -> {
			System.out.println(message.body());
		});

		eb.consumer("ADDRESS", message -> {
			System.out.println(message.body());
		});

		eb.publish("ADDRESS", "2345");
		
	}

}
