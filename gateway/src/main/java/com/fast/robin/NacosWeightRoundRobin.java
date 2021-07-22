package com.fast.robin;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基于nacos的权重负载均衡
 */
public class NacosWeightRoundRobin extends WeightRoundRobin{
	private static final Logger log = LoggerFactory.getLogger(NacosWeightRoundRobin.class);
	public com.netflix.loadbalancer.Server choose(List<? extends com.netflix.loadbalancer.Server> servers){
		return super.getServer(init(servers));
	}
	
	public synchronized List<Server> init(List<? extends com.netflix.loadbalancer.Server> servers){
		List<Server> serverList = servers.stream().map(server-> {
			Map<String, String> metadata = ((NacosServer)server).getMetadata();
			String weight = metadata.get("weight");
			return new Server(server, StringUtils.isBlank(weight) ? 1 : Integer.valueOf(weight));
		}).collect(Collectors.toList());
		
		boolean update = super.servers.containsAll(serverList);
		
		if (!update) {
			maxWeight = greatestWeight(serverList);
			gcdWeight = greatestCommonDivisor(serverList);
			serverCount = serverList.size();
			super.servers = serverList;
		}
		return serverList;
	}



 
}
