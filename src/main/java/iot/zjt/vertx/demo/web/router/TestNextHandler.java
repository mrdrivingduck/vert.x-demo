package iot.zjt.vertx.demo.web.router;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class TestNextHandler {

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        Route route1 = router.route("/test/next/handler/");
        route1.handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.setChunked(true);
            response.write("route1\n");
            routingContext.next();
            // routingContext.response().end();
        }).handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.write("route2\n");
            routingContext.next();
        }).blockingHandler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.write("route3\n");
            routingContext.next();
            // routingContext.response().end();
        });

        Route route2 = router.route("/test/next/handler/");
        route2.handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.write("route4\n");
            routingContext.response().end();
        });

        server.requestHandler(router::accept).listen(9001, "localhost");


        HttpClient client = vertx.createHttpClient();
        client.getNow(9001, "localhost", "/test/next/handler/", response -> {
            response.bodyHandler(totalBuffer -> {
                System.out.println(totalBuffer.toString());
            });
        });
    }
}