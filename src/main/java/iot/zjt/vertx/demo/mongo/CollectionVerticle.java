package iot.zjt.vertx.demo.mongo;

import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class CollectionVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        JsonObject mongoConfig = new JsonObject()
            .put("connection_string", "mongodb://localhost:27017")
            .put("db_name", "test");
        MongoClient mongoClient = MongoClient.createShared(vertx, mongoConfig);

        mongoClient.getCollections(res -> {
            if (res.succeeded()) {
                List<String> connections = res.result();
                for (String connection : connections) {
                    System.out.println(connection);
                }
            } else {
                res.cause().printStackTrace();
            }
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}