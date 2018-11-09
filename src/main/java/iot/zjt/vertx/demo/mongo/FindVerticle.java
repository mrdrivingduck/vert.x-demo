package iot.zjt.vertx.demo.mongo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class FindVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        JsonObject mongoConfig = new JsonObject()
            .put("connection_string", "mongodb://localhost:27017")
            .put("db_name", "test");
        MongoClient mongoClient = MongoClient.createShared(vertx, mongoConfig);

        JsonObject query = new JsonObject().put("title", "Adult");
        mongoClient.find("books", query, res -> {
            if (res.succeeded()) {
                for (JsonObject json : res.result()) {
                    System.out.println(json.encodePrettily());
                }
            } else {
                res.cause().printStackTrace();
            }
        });

        // FindOptions options = new FindOptions()
        //     .setFields(new JsonObject().put("title", "Adult"));
        // mongoClient.findWithOptions("books", query, options, res -> {
        //     if (res.succeeded()) {
        //         for (JsonObject json : res.result()) {
        //             System.out.println(json.encodePrettily());
        //         }
        //     } else {
        //         res.cause().printStackTrace();
        //     }
        // });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}