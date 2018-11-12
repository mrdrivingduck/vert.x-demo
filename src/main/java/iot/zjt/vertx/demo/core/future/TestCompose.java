package iot.zjt.vertx.demo.core.future;

import java.util.List;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.ext.mongo.MongoClient;

public class TestCompose {

    public static void main(String[] args) {
        
        Future<Void> endFuture = Future.future();

        Future<HttpServer> httpFuture = Future.future();

        Vertx vertx = Vertx.vertx();
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(request -> {
            request.response().end();
        });
        httpServer.listen(httpFuture.completer());

        httpFuture.compose(server -> {
            // 'server' is a 'HttpServer' object
            System.out.println("HTTP Server listening");
            
            Future<NetServer> netFuture = Future.future();
            NetServer netServer = vertx.createNetServer();
            netServer.connectHandler(socket -> {
                socket.end();
            });
            netServer.listen(netFuture.completer());
            return netFuture;
        }).compose(server -> {
            // 'server' is a 'NetServer' object
            System.out.println("TCP Server listening");
            
            Future<List<String>> mongoFuture = Future.future();
            JsonObject mongoConfig = new JsonObject()
                .put("connection_string", "mongodb://localhost:27017")
                .put("db_name","test");
            MongoClient mongoClient = MongoClient.createShared(vertx, mongoConfig);
            mongoClient.getCollections(mongoFuture.completer());
            return mongoFuture;
        }).compose(res -> {
            // 'res' is a 'List<String>' object
            System.out.println("DB Operation succeeded");
            endFuture.complete();
        }, endFuture);
        
        endFuture.setHandler(v -> {
            System.out.println("Ending");
        });
    }
}