package iot.zjt.vertx.demo.mongo;

import io.vertx.core.Vertx;

public class TestFind {

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();
        FindVerticle findVerticle = new FindVerticle();
        vertx.deployVerticle(findVerticle);
    }
}