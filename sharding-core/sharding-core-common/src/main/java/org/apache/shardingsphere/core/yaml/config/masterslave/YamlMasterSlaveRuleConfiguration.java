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

package org.apache.shardingsphere.core.yaml.config.masterslave;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.underlying.common.yaml.config.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Master-slave rule configuration for YAML.
 */
@Getter
@Setter
public class YamlMasterSlaveRuleConfiguration implements YamlConfiguration {
    
    private String name;
    
    private String masterDataSourceName;
    
    private List<String> slaveDataSourceNames = new ArrayList<>();

    /**
     * 关于从库选择的负载均衡算法类型，根据类型选择通过SPI注册的算法实现
     */
    private String loadBalanceAlgorithmType;
    
    private Properties props = new Properties();
}
