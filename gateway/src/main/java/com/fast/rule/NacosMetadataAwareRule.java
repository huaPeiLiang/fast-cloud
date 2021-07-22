package com.fast.rule;


import com.fast.predicate.NacosMetadataAwarePredicate;

public class NacosMetadataAwareRule extends DiscoveryEnabledRule {

    public NacosMetadataAwareRule() {
        super(new NacosMetadataAwarePredicate());
    }
}