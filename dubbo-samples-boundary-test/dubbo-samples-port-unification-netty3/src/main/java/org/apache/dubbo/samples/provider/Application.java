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

package org.apache.dubbo.samples.provider;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.samples.api.GreetingService;
import org.apache.dubbo.samples.zookeeper.EmbeddedZooKeeper;

import java.util.concurrent.CountDownLatch;

public class Application {


    public static void main(String[] args) throws Exception {

        new EmbeddedZooKeeper(2181, false).start();
        ProtocolConfig protocolConfig = new ProtocolConfig(CommonConstants.TRIPLE, -1);
        protocolConfig.setExtProtocol(CommonConstants.DUBBO);
        protocolConfig.setServer("netty3");
        ServiceConfig<GreetingService> service = new ServiceConfig<>();
        service.setApplication(new ApplicationConfig("first-dubbo-provider"));
        service.setRegistry(new RegistryConfig(
                "zookeeper://" + "127.0.0.1" + ":" + "2181"));
        service.setInterface(GreetingService.class);
        service.setRef(new GreetingServiceImpl());
        service.setProtocol(protocolConfig);
        service.export();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}