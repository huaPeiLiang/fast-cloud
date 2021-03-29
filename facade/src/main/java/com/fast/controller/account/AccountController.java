package com.fast.controller.account;


import com.fast.api.account.AccountApi;
import com.fast.model.FastRunTimeException;
import com.fast.model.ReturnData;
import com.fast.model.account.request.AccountPageRequest;
import com.fast.model.account.request.AccountTransferRequest;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private AccountApi accountApi;

    @GetMapping(value = "/get/by-id")
    public ReturnData getAccountById(@RequestParam("id") int id){
        try{
            return ReturnData.success(accountApi.getAccountById(id));
        }catch (FastRunTimeException e){
            return ReturnData.failed(e.getMessage());
        } catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

    @PostMapping(value = "/page")
    public ReturnData page(@RequestBody @Valid AccountPageRequest requestVo){
        try{
            return ReturnData.success(accountApi.page(requestVo));
        }catch (FastRunTimeException e){
            return ReturnData.failed(e.getMessage());
        } catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

    @PostMapping(value = "/transfer")
    @GlobalTransactional
    public ReturnData transfer(@RequestBody @Valid AccountTransferRequest requestVo){
        accountApi.transfer(requestVo);
        int i = 1/0;
        return ReturnData.success();
    };

}