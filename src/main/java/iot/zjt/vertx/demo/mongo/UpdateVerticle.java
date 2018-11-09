package iot.zjt.vertx.demo.mongo;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.UpdateOptions;

public class UpdateVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        JsonObject mongoConfig = new JsonObject()
            .put("connection_string", "mongodb://localhost:27017")
            .put("db_name", "test");
        MongoClient mongoClient = MongoClient.createShared(vertx, mongoConfig);

        JsonObject query = new JsonObject().put("_id", "2222");
        JsonObject update = new JsonObject()
            .put("$set", new JsonObject().put("authro", "zjt666"));
        mongoClient.updateCollection("books", query, update, res -> {
            if (res.succeeded()) {
                System.out.println("Succees");
            } else {
                res.cause().printStackTrace();
            }
        });

        JsonObject query1 = new JsonObject().put("_id", "2223");
        UpdateOptions options = new UpdateOptions().setUpsert(true);
        mongoClient.updateCollectionWithOptions("books", query1, update, options, res -> {
            if (res.succeeded()) {
                System.out.println("objk");
            } else {
                System.out.println("failed");
            }
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}