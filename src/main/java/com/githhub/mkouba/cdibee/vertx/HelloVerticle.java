/*
 * JBoss, Home of Professional Open Source
 * Copyright 2017, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.githhub.mkouba.cdibee.vertx;

import org.jboss.weld.vertx.WeldVerticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * {@link Verticle} responsible for starting Weld container and webserver.
 * 
 * <p>
 * This verticle makes use of {@link WeldVerticle} - a verticle provided by <tt>weld-vertx-core</tt> artifact. For more info check
 * <a href="https://github.com/weld/weld-vertx">https://github.com/weld/weld-vertx</a>
 * </p>
 *
 * @author Martin Kouba
 */
public class HelloVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        // This Verticle starts Weld SE container
        final WeldVerticle weldVerticle = new WeldVerticle();

        vertx.deployVerticle(weldVerticle, r -> {

            if (r.succeeded()) {

                // Let's configure the router after Weld bootstrap finished
                Router router = Router.router(vertx);
                router.route().handler(BodyHandler.create());

                // The handler matches all HTTP methods and accepts all content types
                // Note the bean instance used as a blocking handler
                router.route().path("/hello").blockingHandler(weldVerticle.container().select(HelloRouteHandler.class).get());

                // Now start the webserver
                vertx.createHttpServer().requestHandler(router::accept).listen(8080, (listen) -> {
                    if (listen.succeeded()) {
                        startFuture.complete();
                    } else {
                        startFuture.fail("HTTP server not listening: " + listen.cause().getMessage());
                    }
                });

            } else {
                startFuture.fail("Weld verticle failure");
            }
        });
    }

}
