package com.fast.repository;

import com.fast.model.account.root.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findFirstByIdAndAmountIsGreaterThanEqual(Integer accountId, Double amount);

}
