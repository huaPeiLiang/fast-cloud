package com.fast.repository;

import com.fast.model.account.root.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> , JpaSpecificationExecutor<Account> {

    Optional<Account> findFirstByIdAndAmountIsGreaterThanEqual(Integer accountId, Double amount);
}
