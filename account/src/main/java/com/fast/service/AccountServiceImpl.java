package com.fast.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fast.api.record.RecordApi;
import com.fast.mapper.AccountMapper;
import com.fast.model.FastRunTimeException;
import com.fast.model.account.request.AccountPageRequest;
import com.fast.model.account.request.AccountTransferRequest;
import com.fast.model.account.root.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class AccountServiceImpl {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RecordApi recordApi;

    public Account getAccountById(int id){
        return accountMapper.selectById(id);
    }

    public Page<Account> page(AccountPageRequest request){
        Page<Account> page = new Page<>();
        page.setOptimizeCountSql(true);
        return accountMapper.pageAccount(page, request);
    }

    @Transactional
    public void transfer(AccountTransferRequest requestVo){
        Account sourceAccount = accountMapper.findByIdAndAmountIsGreaterThanEqual(requestVo.getSourceAccountId(), requestVo.getAmount());
        if (ObjectUtils.isEmpty(sourceAccount)){
            throw new FastRunTimeException("转账人账号不存在,或金额不足");
        }
        Account targetAccount = accountMapper.selectById(requestVo.getTargetAccountId());
        if (ObjectUtils.isEmpty(targetAccount)){
            throw new FastRunTimeException("收款人账号不存在");
        }

        // 从转账人账户扣款
        sourceAccount.setAmount(sourceAccount.getAmount()-requestVo.getAmount());
        accountMapper.updateById(sourceAccount);

        // 向目标账户打款
        targetAccount.setAmount(targetAccount.getAmount()+requestVo.getAmount());
        accountMapper.updateById(targetAccount);

        // 添加转账记录
        recordApi.add(requestVo.getSourceAccountId(),requestVo.getAmount(),"支出");
        recordApi.add(requestVo.getTargetAccountId(),requestVo.getAmount(),"收入");
    }

}
