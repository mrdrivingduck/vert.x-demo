package iot.zjt.vertx.demo.core.tcp;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

/**
 * @author Mr Dk.
 */

public class TestNetServer {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        NetServer server = vertx.createNetServer();
        server.connectHandler(socket -> {
            socket.handler(buffer -> {
                System.out.println("Get:" + buffer.length());
                socket.write("I love u");
                socket.close();
            });
            socket.closeHandler(res -> {
                System.out.println("Socket closed");
                server.close(serverRes -> {
                    if (serverRes.succeeded()) {
                        System.out.println("Server closed");
                        vertx.close();
                    } else {
                        System.out.println("Server closed failed");
                    }
                });
            });
        });
        server.listen(9000, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening at:" + res.result().actualPort());
            } else {
                System.out.println("Server failed to bind");
            }
        });

        NetClientOptions options = new NetClientOptions()
            .setConnectTimeout(2000)
            .setReconnectAttempts(4)
            .setReconnectInterval(2000);
        NetClient client = vertx.createNetClient(options);
        client.connect(9000, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("Client connected");
                NetSocket socket = res.result();
                Buffer buf = Buffer.buffer().appendInt(666).appendString("I love u");
                socket.write(buf);
            } else {
                System.out.println(res.cause().getMessage());
            }
        });

    }

}
