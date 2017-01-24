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
package com.github.mkouba.cdibee;

import static org.junit.Assert.assertEquals;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;

import com.githhub.mkouba.cdibee.HelloService;

/**
 * These tests demonstrate the basic usage of Weld SE Bootstrap API.
 *
 * @author Martin Kouba
 * @see Weld
 * @see WeldContainer
 */
public class HelloServiceTest {

    @Test
    public void testHelloService1() {
        WeldContainer container = new Weld().initialize();
        HelloService helloService = container.select(HelloService.class).get();
        assertEquals("Hello world!", helloService.hello("world"));
        // What's wrong with this approach?
        container.shutdown();
    }

    @Test
    public void testHelloService2() {
        // Use try-with-resources to shutdown Weld correctly
        try (WeldContainer container = new Weld().initialize()) {
            HelloService helloService = container.select(HelloService.class).get();
            assertEquals("Hello world!", helloService.hello("world"));
        }
    }

    @Test
    public void testHelloService3() {
        // Start Weld with bootstrap optimized for tests
        try (WeldContainer container = new Weld()
                .disableDiscovery()
                .packages(HelloService.class)
                .property("org.jboss.weld.bootstrap.concurrentDeployment", false)
                .initialize()) {
            HelloService helloService = container.select(HelloService.class).get();
            assertEquals("Hello world!", helloService.hello("world"));
        }
    }

}
