package iot.zjt.vertx.demo.web.router;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class TestRegexCapturePathParam {

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.routeWithRegex(".*123")
            .pathRegex("\\/([^\\/]+)\\/([^\\/]+)")
            .handler(routingContext -> {
                String name = routingContext.request().getParam("param0");
                String passwd = routingContext.request().getParam("param1");
                System.out.println(name);
                System.out.println(passwd);
                routingContext.next();
            });
        router.routeWithRegex(".*123")
            .pathRegex("\\/(?<name>[^\\/]+)\\/(?<passwd>[^\\/]+)")
            .handler(routingContext -> {
                String name = routingContext.request().getParam("name");
                String passwd = routingContext.request().getParam("passwd");
                System.out.println(name);
                System.out.println(passwd);
                routingContext.request().response().end("I love u");
            });
        server.requestHandler(router::accept).listen(9001, "localhost");

        /**
         * 正则表达式含义：
         *     / ([^/]+) / ([^/]+)
         *     匹配不含 '/' 的子表达式一次或多次，保证 URL 只有两个 '/'，即只有两个参数
         */

        HttpClient client = vertx.createHttpClient();
        client.getNow(9001, "localhost", "/zjt/123", response -> {
            response.bodyHandler(totalBuffer -> {
                System.out.println(totalBuffer.length());
            });
        });
    }
}