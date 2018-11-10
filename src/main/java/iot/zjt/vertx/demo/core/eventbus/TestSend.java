package iot.zjt.vertx.demo.core.eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.eventbus.MessageConsumer;

/**
 * @author Mr Dk.
 */

public class TestSend {

	public static void main(String[] args) {

		Vertx vertx = Vertx.vertx();
		EventBus eb = vertx.eventBus();
		MessageCodec<Entity, Entity> mCodec = new MessageCodec<Entity, Entity>() {

			@Override
			public void encodeToWire(Buffer buffer, Entity s) {
				
			}

			@Override
			public Entity decodeFromWire(int pos, Buffer buffer) {
				return null;
			}

			@Override
			public Entity transform(Entity s) {
				return s;
			}

			@Override
			public String name() {
				return "EntityCodec";
			}

			@Override
			public byte systemCodecID() {
				return -1;
			}
		};
		eb.registerDefaultCodec(Entity.class, mCodec);

		MessageConsumer<Entity> mc1 = eb.consumer("ADDRESS");
		mc1.handler(message -> {
			Entity entity = (Entity) message.body();
			System.out.println("mc1 got msg from:" + entity.getName());
			message.reply("mc1 got message", reply -> {
				System.out.println(reply.result().body());
				vertx.close();
			});
		});

		MessageConsumer<Entity> mc2 = eb.consumer("ADDRESS");
		mc2.handler(message -> {
			Entity entity = (Entity) message.body();
			System.out.println("mc2 got msg from:" + entity.getName());
			message.reply("mc2 got message");
		});

		Entity entity = new Entity();
		entity.setName("Mr Dk.");
		entity.setAge(21);
		entity.setPrice(2565433.65);
		eb.send("ADDRESS", entity, req -> {
			if (req.succeeded()) {
				System.out.println("Get reply:" + req.result().body());
				req.result().reply("666");
			}
		});
		

	}

}
