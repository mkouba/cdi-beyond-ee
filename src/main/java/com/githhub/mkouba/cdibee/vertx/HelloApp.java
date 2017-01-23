package com.githhub.mkouba.cdibee.vertx;

import io.vertx.core.Vertx;

/**
 * <pre>
 * curl http://localhost:8080/hello?name=Martin
 * </pre>
 *
 * @author Martin Kouba
 */
public class HelloApp {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new HelloVerticle());
    }
}
