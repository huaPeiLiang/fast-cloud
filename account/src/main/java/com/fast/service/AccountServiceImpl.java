//package com.fast.service;
//
//import com.fast.api.record.RecordApi;
//import com.fast.model.account.request.AccountPageRequest;
//import com.fast.model.account.request.AccountTransferRequest;
//import com.fast.model.account.root.Account;
//import com.fast.repository.AccountRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//public class AccountServiceImpl {
//
//    @Autowired
//    private AccountRepository accountRepository;
//    @Autowired
//    private RecordApi recordApi;
//
//    public Account getAccountById(int id){
//        return accountRepository.findById(id).orElseGet(Account::new);
//    }
//
//    public Page<Account> page(AccountPageRequest requestVo){
//        return accountRepository.findAll(requestVo.build(), requestVo.getPageRequest());
//    }
//
//    @Transactional
//    public void transfer(AccountTransferRequest requestVo){
//        Optional<Account> sourceAccountOp = accountRepository.findFirstByIdAndAmountIsGreaterThanEqual(requestVo.getSourceAccountId(), requestVo.getAmount());
//        if (!sourceAccountOp.isPresent()){
//            throw new RuntimeException("转账人账号不存在,或金额不足");
//        }
//        Optional<Account> targetAccountOp = accountRepository.findById(requestVo.getTargetAccountId());
//        if (!targetAccountOp.isPresent()){
//            throw new RuntimeException("收款人账号不存在");
//        }
//
//        // 从转账人账户扣款
//        Account sourceAccount = sourceAccountOp.get();
//        sourceAccount.setAmount(sourceAccount.getAmount()-requestVo.getAmount());
//        accountRepository.save(sourceAccount);
//
//        // 向目标账户打款
//        Account targetAccount = targetAccountOp.get();
//        targetAccount.setAmount(targetAccount.getAmount()+requestVo.getAmount());
//        accountRepository.save(targetAccount);
//
//        // 添加转账记录
//        recordApi.add(requestVo.getSourceAccountId(),requestVo.getAmount(),"支出");
//        recordApi.add(requestVo.getTargetAccountId(),requestVo.getAmount(),"收入");
//    }
//
//}
