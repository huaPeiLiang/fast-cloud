package com.fast.robin;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 权重负载均衡
 */
public abstract class WeightRoundRobin{
	private static final Logger logger = LoggerFactory.getLogger(WeightRoundRobin.class);
	/**上次选择的服务器*/
	protected int currentIndex = -1;
	/**当前调度的权值*/
	protected int currentWeight = 0;
	/**最大权重*/
	protected int maxWeight;
	/**权重的最大公约数*/
	protected int gcdWeight;
	/**服务器数*/
	protected int serverCount;
	protected List<Server> servers = new ArrayList<Server>();
	
	public int greaterCommonDivisor(int a, int b){
		BigInteger aBig = new BigInteger(String.valueOf(a));
		BigInteger bBig = new BigInteger(String.valueOf(b));
		return aBig.gcd(bBig).intValue();
	}
	
	public int greatestCommonDivisor(List<Server> servers){
		int divisor = 0;
		for(int index = 0, len = servers.size(); index < len - 1; index++){
			if(index ==0){
				divisor = greaterCommonDivisor(
							servers.get(index).getWeight(), servers.get(index + 1).getWeight());
			}else{
				divisor = greaterCommonDivisor(divisor, servers.get(index).getWeight());
			}
		}
		return divisor;
	}
	
	public int greatestWeight(List<Server> servers){
		int weight = 0;
		for(Server server : servers){
			if(weight < server.getWeight()){
				weight = server.getWeight();
			}
		}
		return weight;
	}
	
	/**
    *  算法流程： 
    *  假设有一组服务器 S = {S0, S1, …, Sn-1}
    *  有相应的权重，变量currentIndex表示上次选择的服务器
    *  权值currentWeight初始化为0，currentIndex初始化为-1 ，当第一次的时候返回 权值取最大的那个服务器，
    *  通过权重的不断递减 寻找 适合的服务器返回，直到轮询结束，权值返回为0 
    */
	public com.netflix.loadbalancer.Server getServer(List<Server> servers){
		if(servers.size()==1){
			return servers.get(0).getServer();
		}
		//logger.info("getServer: serverCount:{}",serverCount);
		while(true){
			currentIndex = (currentIndex + 1) % serverCount;
			if(currentIndex == 0){
				currentWeight = currentWeight - gcdWeight;
				if(currentWeight <= 0){
					currentWeight = maxWeight;
					if(currentWeight == 0){
						return null;
					}
				}
			}
			//logger.info("getServer: currentIndex:{} currentWeight:{}",serverCount,currentWeight);
			if(servers.get(currentIndex).getWeight() >= currentWeight){
				return  servers.get(currentIndex).getServer();
			}
		}
	}
	

	
	public abstract com.netflix.loadbalancer.Server choose(List<? extends com.netflix.loadbalancer.Server> servers);
	
	
	static class Server {
		
		com.netflix.loadbalancer.Server server;
		Integer weight;
		
		public Server(com.netflix.loadbalancer.Server server, Integer weight) {
			this.server = server;
			this.weight = weight;
		}
		
		public com.netflix.loadbalancer.Server getServer() {
			return server;
		}
		
		public void setServer(com.netflix.loadbalancer.Server server) {
			this.server = server;
		}
		
		public Integer getWeight() {
			return weight;
		}
		
		public void setWeight(Integer weight) {
			this.weight = weight;
		}
		
		@Override
		public int hashCode() {
			return Objects.hashCode(server.getHostPort())+Objects.hashCode(weight);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (obj instanceof Server){
				Server server = (Server) obj;
				return Objects.equals(this.server.getHostPort(),server.getServer().getHostPort()) &&
						Objects.equals(this.weight,server.weight);
			} else {
				return false;
			}
			
			
		}
	}

 
}
