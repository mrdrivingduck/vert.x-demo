package iot.zjt.vertx.demo.mongo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class RemoveVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        JsonObject mongoConfig = new JsonObject()
            .put("connection_string", "mongodb://localhost:27017")
            .put("db_name", "test");
        MongoClient mongoClient = MongoClient.createShared(vertx, mongoConfig);

        JsonObject query = new JsonObject().put("_id", "2223");
        mongoClient.removeDocuments("books", query, res -> {
            if (res.succeeded()) {
                System.out.println("Succees");
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