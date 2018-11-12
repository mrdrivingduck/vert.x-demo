package iot.zjt.vertx.demo.core.future;

import java.util.Arrays;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.net.NetServer;

public class TestCompositeFuture {

    public static void main(String[] args) {
        Future<HttpServer> httpServerFuture = Future.future();
        Future<NetServer> netServerFuture = Future.future();

        Vertx vertx = Vertx.vertx();
        HttpServer httpServer = vertx.createHttpServer();
        NetServer netServer = vertx.createNetServer();
        httpServer.requestHandler(request -> {
            request.response().end();
        });
        netServer.connectHandler(socket -> {
            socket.close();
        });

        httpServer.listen(httpServerFuture.completer());  // Complete directly
        netServer.listen(server -> {                      // Implement completion
            // server is an 'AsyncResult<NetServer>' object
            if (server.succeeded()) {
                netServerFuture.complete();
            } else {
                netServerFuture.fail(server.cause().getMessage());
            }
        });

        CompositeFuture.all(Arrays.asList(httpServerFuture, netServerFuture)).setHandler(ar -> {
            // ar is an 'AsyncResult<CompositeFuture>' object
            if (ar.succeeded()) {
                // All futures succeeded
                System.out.println("All succeeded");
            } else {
                // At least one future failed
                System.out.println("At least one failed");
            }
        });
    }
}