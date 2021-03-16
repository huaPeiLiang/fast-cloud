package com.fast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fast.model.account.request.AccountPageRequest;
import com.fast.model.account.root.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    Page<Account> pageAccount(Page<Account> page,@Param("request") AccountPageRequest request);

    @Select("select * from account where id = ${sourceAccountId} and amount >= ${amount}")
    Account findByIdAndAmountIsGreaterThanEqual(@Param("sourceAccountId") Integer sourceAccountId, @Param("amount") Double amount);
}
