package iot.zjt.vertx.demo.mongo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class CountVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        JsonObject mongoConfig = new JsonObject()
            .put("connection_string", "mongodb://localhost:27017")
            .put("db_name", "test");
        MongoClient mongoClient = MongoClient.createShared(vertx, mongoConfig);

        JsonObject query = new JsonObject().put("title", "Adult");
        mongoClient.count("books", query, res -> {
            if (res.succeeded()) {
                long num = res.result();
                System.out.println(num + " rows affected");
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