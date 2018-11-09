package iot.zjt.vertx.demo.mongo;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class TestSave {

    // https://stackoverflow.com/questions/31933821/cant-connect-to-local-monogodb-from-java

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();
        DeploymentOptions options = new DeploymentOptions().setWorker(true);
        SaveVerticle dbVerticle = new SaveVerticle();
        vertx.deployVerticle(dbVerticle, options);
    }


}