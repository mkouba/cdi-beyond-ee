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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import com.githhub.mkouba.cdibee.HelloMessage;

@ApplicationScoped
public class DummyObserver {

    private List<String> messages;

    @PostConstruct
    void init() {
        messages = new ArrayList<>();
    }

    public void observeHelloMessage(@Observes @HelloMessage String message) {
        messages.add(message);
    }

    // Note that this bean is @ApplicationScoped and so may not access the "messages" field directly
    List<String> getMessages() {
        return messages;
    }

}