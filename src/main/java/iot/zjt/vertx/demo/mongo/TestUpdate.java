package iot.zjt.vertx.demo.mongo;

import io.vertx.core.Vertx;

public class TestUpdate {

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();
        UpdateVerticle updateVerticle = new UpdateVerticle();
        vertx.deployVerticle(updateVerticle);
    }
}