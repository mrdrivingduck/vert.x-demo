package iot.zjt.vertx.demo.mongo;

import io.vertx.core.Vertx;

public class TestCollection {

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();
        CollectionVerticle collectionVerticle = new CollectionVerticle();
        vertx.deployVerticle(collectionVerticle);
    }
}