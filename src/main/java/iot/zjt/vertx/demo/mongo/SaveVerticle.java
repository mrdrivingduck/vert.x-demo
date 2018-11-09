package iot.zjt.vertx.demo.mongo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class SaveVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        JsonObject mongoConfig = new JsonObject()
            .put("connection_string", "mongodb://localhost:27017")
            .put("db_name", "test");
        MongoClient mongoClient = MongoClient.createShared(vertx, mongoConfig);

        JsonObject document = new JsonObject()  // 插入一条记录
            .put("title", "Childdd")
            .put("_id", "2222");
        mongoClient.save("books", document, res -> {
            if (res.succeeded()) {
                String id = res.result();
                System.out.println("id:" + id);
            } else {
                res.cause().printStackTrace();
            }
        });
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stop");
    }
}