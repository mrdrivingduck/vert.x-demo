package iot.zjt.vertx.demo.web.router;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class TestBasicRouter {

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        
        router.route().handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");
            response.end("I love u");
        });
        server.requestHandler(router::accept).listen(9001, "localhost");


        HttpClient client = vertx.createHttpClient();
        client.getNow(9001, "localhost", "/", response -> {
            response.bodyHandler(totalBuffer -> {
                System.out.println(totalBuffer.length());
            });
        });
    }
}