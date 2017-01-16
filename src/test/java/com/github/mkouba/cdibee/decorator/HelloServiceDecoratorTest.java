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
package com.github.mkouba.cdibee.decorator;

import static com.github.mkouba.cdibee.HelloServiceTest.testWeld;
import static org.junit.Assert.assertEquals;

import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;

import com.githhub.mkouba.cdibee.HelloService;
import com.githhub.mkouba.cdibee.HelloServiceDecorator;

/**
 * This test demonstrates the usage of Weld SE to test functionality provided by {@link HelloServiceDecorator}.
 *
 * @author Martin Kouba
 */
public class HelloServiceDecoratorTest {

    @Test
    public void testDecorator() {
        try (WeldContainer container = testWeld()
                .beanClasses(HelloService.class, HelloServiceDecorator.class, DummyHelloService.class)
                .initialize()) {
            // Obtain dummy HelloService
            HelloService helloService = container.select(HelloService.class).get();
            // Test forbidden word
            assertEquals("<censored>", helloService.hello("poo"));
            // Test allowed word
            assertEquals("Martin", helloService.hello("Martin"));
        }
    }

}
