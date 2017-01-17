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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Implementation of {@link HelloService} which also fires an event whenever a unique hello message (event payload) is constructed.
 *
 * @author Martin Kouba
 */
@ApplicationScoped
public class EventHelloService implements HelloService {

    private List<String> fired;

    @Inject
    @HelloMessage
    private Event<String> event;

    public String hello(String name) {
        String message = HelloService.super.hello(name);
        if (!fired.contains(message)) {
            fired.add(message);
            event.fire(message);
        }
        return message;
    }

    @PostConstruct
    void init() {
        fired = new CopyOnWriteArrayList<>();
    }

}
