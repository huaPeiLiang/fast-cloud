package com.fast.configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.fast.model.FastRunTimeException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan("com.fast.mapper")
public class MybatisPlusConfig {

    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                // TODO 集成JWT解析token获取租户ID
                Long tenantId = getTenantIdByHeader();
                if(tenantId == null){
                    throw new FastRunTimeException("获取租户信息失败");
                }
                return new LongValue(tenantId);
            }

            @Override
            public boolean ignoreTable(String tableName) {
                // 存放无需拼接多租户条件的表名
                Map<String,Boolean> tableNameMap = new HashMap<>();
                tableNameMap.put("account",true);
                if (tableNameMap.get(tableName) != null){
                    return tableNameMap.get(tableName);
                }else{
                    return false;
                }
            }
        }));
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

    /**
     * 获取请求头中的租户ID
     * */
    public static Long getTenantIdByHeader() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(servletRequestAttributes, "header 获取异常");
        Long tenantId = null;
        // 获取请求头中的商户id
        try{
            tenantId = Long.parseLong(servletRequestAttributes.getRequest().getHeader("TenantId"));
        }catch (Exception e){
            throw new FastRunTimeException("获取租户信息失败");
        }
        Assert.notNull(tenantId, "获取租户信息失败");
        return tenantId;
    }

}