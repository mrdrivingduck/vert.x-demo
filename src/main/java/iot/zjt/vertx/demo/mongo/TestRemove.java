package iot.zjt.vertx.demo.mongo;

import io.vertx.core.Vertx;

public class TestRemove {

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();
        RemoveVerticle removeVerticle = new RemoveVerticle();
        vertx.deployVerticle(removeVerticle);
    }
}