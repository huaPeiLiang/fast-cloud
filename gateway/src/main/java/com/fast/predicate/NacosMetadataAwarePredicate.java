package com.fast.predicate;


import com.fast.context.RibbonFilterContext;
import com.fast.context.RibbonFilterContextHolder;
import com.fast.robin.NacosWeightRoundRobin;
import com.fast.robin.WeightRoundRobin;
import com.google.common.base.Optional;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;

import java.util.*;


/**
 * Nacos元数据断言，负责根据请求参数，与服务中的元数据比对
 */
public class NacosMetadataAwarePredicate extends DiscoveryEnabledPredicate {
    private static final String KEY_GRAY="gray";

    private static final Logger log = LoggerFactory.getLogger(NacosMetadataAwarePredicate.class);
    WeightRoundRobin weightRoundRobin = new NacosWeightRoundRobin();
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean apply(Server server) {

        final RibbonFilterContext context = RibbonFilterContextHolder.getCurrentContext();
        Map<String,String> contextAttributes=new HashMap<>(context.getAttributes());
        final Map<String, String> metadata = ((NacosServer) server).getMetadata();
        //获得微服务的元数据配置的灰度值
        String serverIsGray=metadata.get(KEY_GRAY);
        //如果请求不包含gray参数，而且服务也不是灰度的，则给请求增加gray=false参数
        if(!contextAttributes.containsKey(KEY_GRAY) && "false".equals(serverIsGray)){
            contextAttributes.put(KEY_GRAY,"false");
        }

        Set<Map.Entry<String,String>> requestParams=new HashSet<>(contextAttributes.entrySet());
        Set<Map.Entry<String,String>> metadataParams =new HashSet<>(metadata.entrySet());
        //请求参数中，移除api版本参数，其它参数留着进行对比
        requestParams.removeIf((entry -> entry.getKey().equals("version")));

        //服务元数据中，移除source参数，其它留着参数进行对比
        metadataParams.removeIf((entry) -> entry.getKey().equals("preserved.register.source"));
        metadataParams.removeIf((entry) -> entry.getKey().equals("management.port"));


        //获得请求参数中的灰度值
        String requestIsGray=context.getAttributes().get(KEY_GRAY);
        boolean matched=false;
        //第1步：先判断请求和元数据的灰度值是否匹配
        if(requestIsGray!=null ){
            matched= requestIsGray.equals(serverIsGray);
        }else{
            matched= serverIsGray==null || "false".equals(serverIsGray);
        }
        //第2步/
        if(matched){
            matched=requestParams.containsAll(metadataParams);
            if(!matched){
                log.info("请求元数据不满足条件，请求:"+requestParams+", 服务："+metadataParams);
            }
        }else{
            log.info("请求不满足灰度条件，请求 gray:"+requestIsGray+", 服务 gray："+serverIsGray);
        }


        return matched;
    }
    
    @Override
    public Optional<Server> chooseRoundRobinAfterFiltering(List<Server> servers, Object loadBalancerKey) {
        List<Server> eligible = getEligibleServers(servers, loadBalancerKey);
        if (eligible.size() == 0) {
            return Optional.absent();
        }
        return Optional.of(weightRoundRobin.choose(eligible));
    }
    
    
}