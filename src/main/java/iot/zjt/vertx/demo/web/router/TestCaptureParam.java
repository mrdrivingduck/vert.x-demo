package iot.zjt.vertx.demo.web.router;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class TestCaptureParam {

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route(HttpMethod.POST, "/test/handler/:name/:passwd")
            .handler(routingContext -> {
                String name = routingContext.request().getParam("name");
                String passwd = routingContext.request().getParam("passwd");
                System.out.println(name);
                System.out.println(passwd);
                routingContext.next();
            });

        router.routeWithRegex(HttpMethod.POST, ".*123")
            .handler(routingContext -> {
                System.out.println("Through regex");
                routingContext.request().response().end();
            });

        server.requestHandler(router::accept).listen(9001, "localhost");

        HttpClient client = vertx.createHttpClient();
        client.post(9001, "localhost", "/test/handler/zjt/123", response -> {
            System.out.println("Get response");
            response.bodyHandler(totalBuffer -> {
                System.out.println(totalBuffer.length());
            });
        }).end();
    }
}