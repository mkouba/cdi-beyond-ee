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

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import io.vertx.core.AbstractVerticle;

/**
 *
 * @author Martin Kouba
 */
public class HelloVerticle extends AbstractVerticle {

    WeldContainer weldContainer;

    @Override
    public void start() throws Exception {
        weldContainer = new Weld()
                .containerId(context.deploymentID())
                .disableDiscovery()
                .packages(HelloService.class)
                .property("org.jboss.weld.bootstrap.concurrentDeployment", false)
                .initialize();
    }

    @Override
    public void stop() throws Exception {
        if (weldContainer != null) {
            weldContainer.shutdown();
        }
    }

    public WeldContainer container() {
        return weldContainer;
    }

}
