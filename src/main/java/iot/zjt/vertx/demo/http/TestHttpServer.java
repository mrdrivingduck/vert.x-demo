package iot.zjt.vertx.demo.http;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;

public class TestHttpServer {

	public static void main(String[] args) {
		
		Vertx vertx = Vertx.vertx();
		HttpServer server = vertx.createHttpServer();
		server.requestHandler(request -> {
			// Header received
			System.out.println(request.remoteAddress());
			System.out.println(request.method());
			System.out.println(request.version());
			System.out.println(request.uri());
			System.out.println(request.path());
			// MultiMap headers = request.headers();
			// MultiMap params = request.params();
			// headers.get("...");
			// params.get("...");

			Buffer totalBuffer = Buffer.buffer();
			request.handler(buffer -> {
				// A chunk of body received
				totalBuffer.appendBuffer(buffer);
			});

			request.endHandler(end -> {
				// Whole body received
				System.out.println("Get:" + totalBuffer.length());
				Buffer buf = Buffer.buffer();
				buf.appendInt(1024).appendString("I love u");
				request.response()
					// .putHeader("Content-Length", Integer.toString(buf.length()))
					.setChunked(true)
					// .write(buf).end();
					.end(buf);
			});
		});
		server.listen(9001, "localhost", res -> {
			if (res.succeeded()) {
				System.out.println("Server listening at:" + res.result().actualPort());
			} else {
				System.out.println("Server failed to bind");
			}
		});

		HttpClient client = vertx.createHttpClient();
		client.getNow(9001, "localhost", "/", response -> {
			response.bodyHandler(totalBuffer -> {
				System.out.println(totalBuffer.length());
			});
		});

	}

}
