# CDI Beyond Java EE

This repository contains the source code of examples for the talk at [DevConf.cz 2017](https://devconf.cz/).

## Build and run the tests

    mvn clean test

## Test cases

### Hello Weld SE Bootstrap API

#### `com.github.mkouba.cdibee.HelloServiceTest`
- demonstrates the basic usage of Weld SE Bootstrap API, `try-with-resources` block and how to start Weld with bootstrap optimized for tests

### Testing CDI components

#### `com.github.mkouba.cdibee.event.EventHelloServiceTest`
- test functionality provided by `com.githhub.mkouba.cdibee.EventHelloService`, i.e. CDI events/observers

#### `com.github.mkouba.cdibee.decorator.HelloServiceDecoratorTest`
- shows how easy it is to test a CDI decorator
- makes use of `WeldInitiator` - a JUnit4 test rule provided by `weld-junit4` artifact
- for more info check https://github.com/weld/weld-junit

### Weld meets Vert.x

#### `com.github.mkouba.cdibee.vertx.HelloVerticleTest`
- demonstrates the usage of `WeldVerticle` - a Vert.x component provided by `weld-vertx-core` artifact
- for more info check https://github.com/weld/weld-vertx

#### Try Probe - look into the internals
Just add `weld-vertx-probe` to the classpath (as defined in `pom.xml`) and set the `org.jboss.weld.development` system property to `true`, e.g. something like:

    java -Dorg.jboss.weld.development=true -cp 'target/cdi-beyond-ee-0.0.1-SNAPSHOT.jar:weld-vertx-probe.jar' com.githhub.mkouba.cdibee.vertx.HelloApp
