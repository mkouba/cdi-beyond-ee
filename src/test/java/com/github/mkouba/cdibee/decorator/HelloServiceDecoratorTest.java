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

import static org.junit.Assert.assertEquals;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Rule;
import org.junit.Test;

import com.githhub.mkouba.cdibee.HelloService;
import com.githhub.mkouba.cdibee.HelloServiceDecorator;

/**
 * This test demonstrates the usage of Weld SE Bootstrap API to test functionality provided by {@link HelloServiceDecorator}.
 * 
 * <p>
 * This test makes use of {@link WeldInitiator} - a test rule provided by <tt>weld-junit4</tt> artifact. For more info check
 * <a href="https://github.com/weld/weld-junit">https://github.com/weld/weld-junit</a>
 * </p>
 * 
 * @author Martin Kouba
 */
public class HelloServiceDecoratorTest {

    @Rule
    public WeldInitiator weld = WeldInitiator.of(HelloServiceDecorator.class, DummyHelloService.class);

    @Test
    public void testDecorator() {
        // First obtain dummy HelloService
        HelloService helloService = weld.select(HelloService.class).get();

        // Test forbidden and allowed words
        assertEquals("<censored>", helloService.hello("poo"));
        assertEquals("Martin", helloService.hello("Martin"));
    }

}
