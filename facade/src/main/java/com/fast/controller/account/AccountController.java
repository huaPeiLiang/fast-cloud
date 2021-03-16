package com.fast.controller.account;


import com.fast.api.account.AccountApi;
import com.fast.model.PageResponse;
import com.fast.model.ReturnData;
import com.fast.model.account.request.AccountPageRequest;
import com.fast.model.account.request.AccountTransferRequest;
import com.fast.model.account.root.Account;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private AccountApi accountApi;

    @GetMapping(value = "/get/by-id")
    public ReturnData getAccountById(@RequestParam("id") int id){
        try{
            return ReturnData.success(accountApi.getAccountById(id));
        }catch (HystrixRuntimeException e){
            return ReturnData.failed(e);
        } catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

    @PostMapping(value = "/page")
    public ReturnData page(@RequestBody @Valid AccountPageRequest requestVo){
        try{
            return ReturnData.success(Optional.ofNullable(accountApi.page(requestVo)).orElseGet( PageResponse<Account>::new));
        }catch (HystrixRuntimeException e){
            return ReturnData.failed(e);
        } catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    }

    @PostMapping(value = "/transfer")
    public ReturnData transfer(@RequestBody @Valid AccountTransferRequest requestVo){
        try{
            accountApi.transfer(requestVo);
            return ReturnData.success();
        }catch (HystrixRuntimeException e){
            return ReturnData.failed(e);
        } catch (Exception e){
            return ReturnData.failed(e.getMessage());
        }
    };

}