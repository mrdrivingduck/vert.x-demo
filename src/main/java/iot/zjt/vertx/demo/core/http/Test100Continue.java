package iot.zjt.vertx.demo.core.http;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;

public class Test100Continue {

    public static void main(String[] args) {
        
        Vertx vertx = Vertx.vertx();

        HttpServer server = vertx.createHttpServer();
        server.requestHandler(request -> {
            System.out.println("Get client's request:" + request.absoluteURI());
            request.response().writeContinue();
            request.bodyHandler(totalBuffer -> {
                System.out.println("Get client's body:" + totalBuffer.length());
            });
        });
        server.listen(9001);

        HttpClient client = vertx.createHttpClient();
        HttpClientRequest request = client.request(HttpMethod.GET, 9001, "localhost", "");
        request.handler(response -> {
            System.out.println("Get response from server");
        });
        request.putHeader("Expect", "100-Continue");
        request.setChunked(true);
        request.sendHead();
        request.continueHandler(v -> {
            // OK to send rest of body
            request.write("data");
            request.write("Some more data");
            request.end();
        });
    }
}