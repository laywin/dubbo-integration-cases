/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Copyright 2014 the original author or authors.
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

package org.apache.dubbo.samples.metrics.springboot;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.samples.metrics.springboot.api.DemoService;
import org.apache.dubbo.samples.metrics.springboot.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication(scanBasePackages = { "org.apache.dubbo"})
@EnableDubbo(scanBasePackages = "org.apache.dubbo")
@ImportResource("classpath*:spring/dubbo-demo-*.xml")
public class MetricsApplication implements CommandLineRunner {

    @DubboReference
    private DemoService demoService;

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        SpringApplication.run(MetricsApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        while (true) {
            try {
                Thread.sleep(3000);
                Result hello = demoService.sayHello("world");
                System.out.println(hello.getMsg());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}