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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.githhub.mkouba.cdibee.HelloRouteHandler;
import com.githhub.mkouba.cdibee.HelloVerticle;

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
        final HelloVerticle helloVerticle = new HelloVerticle();
        vertx.deployVerticle(helloVerticle, deploy -> {
            if (deploy.succeeded()) {
                // Let's configure the router after Weld bootstrap finished
                Router router = Router.router(vertx);
                router.route().handler(BodyHandler.create());

                // Matches all HTTP methods and accepts all content types
                router.route().path("/hello").blockingHandler(helloVerticle.container().select(HelloRouteHandler.class).get());

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
        get("/hello?name=Jay").then().assertThat().statusCode(200).body(equalTo("Hello Jay!"));
    }

}
