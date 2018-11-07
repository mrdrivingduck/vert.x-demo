package iot.zjt.vertx.demo.web.router;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class TestRoutingMethod {

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route("/zjt").method(HttpMethod.GET)
            .handler(routingContext -> {
                // routingContext.request().response().end("GET");
                routingContext.request().response().setChunked(true);
                routingContext.request().response().write("GET\n");
                routingContext.next();
            });
        router.route(HttpMethod.POST, "/zjt")
            .handler(routingContext -> {
                // routingContext.request().response().end("POST\n");
                routingContext.request().response().setChunked(true);
                routingContext.request().response().write("POST\n");
                routingContext.next();
            });
        router.get("/zjt")
            .handler(routingContext -> {
                routingContext.request().response().write("Another GET\n");
                routingContext.next();
            });
        router.route("/zjt")
            .method(HttpMethod.POST).method(HttpMethod.GET)
            .handler(routingContext -> {
                routingContext.request().response().end("GET & POST");
            });
        server.requestHandler(router::accept).listen(9001, "localhost");

        HttpClient client = vertx.createHttpClient();
        client.get(9001, "localhost", "/zjt", response -> {
            response.bodyHandler(totalBuffer -> {
                System.out.println(totalBuffer.toString());
            });
        }).end();
        client.post(9001, "localhost", "/zjt", response -> {
            response.bodyHandler(totalBuffer -> {
                System.out.println(totalBuffer.toString());
            });
        }).end();
    }
}