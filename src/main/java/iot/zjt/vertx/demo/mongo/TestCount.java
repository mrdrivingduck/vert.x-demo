package iot.zjt.vertx.demo.mongo;

import io.vertx.core.Vertx;

public class TestCount {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        CountVerticle countVerticle = new CountVerticle();
        vertx.deployVerticle(countVerticle);
    }
}