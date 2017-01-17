/*
 * Copyright 2017 Martin Kouba
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mkouba.cdibee.vertx;

import static io.restassured.RestAssured.get;
import static org.hamcrest.core.IsEqual.equalTo;

import org.jboss.weld.vertx.WeldVerticle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.githhub.mkouba.cdibee.HelloRouteHandler;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 *
 * @author Martin Kouba
 */
@RunWith(VertxUnitRunner.class)
public class HelloRouteHandlerTest {

    private Vertx vertx;

    @Before
    public void init(TestContext context) {
        vertx = Vertx.vertx();
        Async async = context.async();

        // This Verticle starts Weld SE container
        final WeldVerticle weldVerticle = new WeldVerticle();

        vertx.deployVerticle(weldVerticle, deploy -> {
            if (deploy.succeeded()) {
                // Let's configure the router after Weld bootstrap finished
                Router router = Router.router(vertx);
                router.route().handler(BodyHandler.create());

                // Matches all HTTP methods and accepts all content types
                // We pass a bean instance as a blocking handler
                router.route().path("/hello").blockingHandler(weldVerticle.container().select(HelloRouteHandler.class).get());

                // Now start the webserver
                vertx.createHttpServer().requestHandler(router::accept).listen(8080, (listen) -> {
                    if (listen.succeeded()) {
                        async.complete();
                    }
                });
            }
        });
    }

    @After
    public void close(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testHelloRoute() {
        // We're using RestAssured API
        get("http;//localhost:8080/hello?name=Jay").then().assertThat().statusCode(200).body(equalTo("Hello Jay!"));
    }

}
