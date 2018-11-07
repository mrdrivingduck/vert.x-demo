package iot.zjt.vertx.demo.core.abstractverticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class TestAbstractVerticle {

	public static void main(String[] args) {
		
		Vertx vertx = Vertx.vertx();

		MyVerticle myVerticle = new MyVerticle();
		JsonObject config = new JsonObject().put("key", "value");
		DeploymentOptions options = new DeploymentOptions().setConfig(config);
		vertx.deployVerticle(myVerticle, options, res -> {
			System.out.println("Deployed");

			vertx.undeploy(res.result(), unres -> {
				System.out.println("Undeployed");
				vertx.close();
			});
		});

	}

}
