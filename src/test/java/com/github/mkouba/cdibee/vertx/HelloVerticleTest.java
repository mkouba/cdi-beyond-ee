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

import com.githhub.mkouba.cdibee.vertx.HelloVerticle;

import io.restassured.response.Response;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

/**
 *
 * @author Martin Kouba
 */
@RunWith(VertxUnitRunner.class)
public class HelloVerticleTest {

    private Vertx vertx;

    @Before
    public void init(TestContext context) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(new HelloVerticle(), context.asyncAssertSuccess());
    }

    @After
    public void close(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test(timeout = 10000)
    public void testHelloRoute() {
        // We're using RestAssured API
        Response response = get("http://localhost:8080/hello?name=Jay");
        response.then().assertThat().statusCode(200).body(equalTo("Hello Jay!"));
    }

}
