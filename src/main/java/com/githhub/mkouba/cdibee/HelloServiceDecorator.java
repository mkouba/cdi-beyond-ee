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
package com.githhub.mkouba.cdibee;

import javax.annotation.Priority;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

/**
 * This decorator filters out forbidden words.
 *
 * @author Martin Kouba
 */
@Priority(1)
@Decorator
public class HelloServiceDecorator implements HelloService {

    private static final String[] FORBIDDEN_WORDS = { "poo" };

    @Inject
    @Delegate
    HelloService delegate;

    public String hello(String name) {
        if (name != null) {
            for (String word : FORBIDDEN_WORDS) {
                if (name.toLowerCase().equals(word.toLowerCase())) {
                    name = "<censored>";
                }
            }
        }
        return delegate.hello(name);
    }

}