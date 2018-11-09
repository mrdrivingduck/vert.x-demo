package iot.zjt.vertx.demo.mongo;

import io.vertx.core.Vertx;

public class TestReplace {

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();
        ReplaceVerticle replaceVerticle = new ReplaceVerticle();
        vertx.deployVerticle(replaceVerticle);
    }
}