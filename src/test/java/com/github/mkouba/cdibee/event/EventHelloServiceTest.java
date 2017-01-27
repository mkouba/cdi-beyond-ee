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
package com.github.mkouba.cdibee.event;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.After;
import org.junit.Test;

import com.githhub.mkouba.cdibee.EventHelloService;
import com.githhub.mkouba.cdibee.HelloMessage;
import com.githhub.mkouba.cdibee.HelloService;

/**
 * This test demonstrates the usage of Weld SE Bootstrap API to test functionality provided by {@link EventHelloService}.
 *
 * @author Martin Kouba
 */
public class EventHelloServiceTest {

    private static List<String> MESSAGES = new ArrayList<>();

    @Test
    public void testHello() {
        try (WeldContainer container = new Weld()
                .disableDiscovery()
                .beanClasses(EventHelloService.class, DummyObserver.class)
                .initialize()) {

            String name = "Brian";
            String expectedMessage = "Hello " + name + "!";

            // Get EventHelloService bean instance
            HelloService helloService = container.select(EventHelloService.class).get();

            // Call hello() - should also fire an event
            assertEquals(expectedMessage, helloService.hello(name));
            assertEquals(1, MESSAGES.size());
            assertEquals(expectedMessage, MESSAGES.get(0));

            // Call hello() again - a new event should not be fired
            assertEquals(expectedMessage, helloService.hello(name));
            assertEquals(1, MESSAGES.size());
            assertEquals(expectedMessage, MESSAGES.get(0));
        }
    }

    @ApplicationScoped
    static class DummyObserver {

        public void observeHelloMessage(@Observes @HelloMessage String message) {
            MESSAGES.add(message);
        }

    }

    @After
    public void cleanup() {
        MESSAGES.clear();
    }

}
