//package com.fast.model.account.request;
//
//import com.fast.model.account.root.Account;
//import lombok.Data;
//import lombok.NonNull;
//import org.springframework.data.jpa.domain.Specification;
//import javax.persistence.criteria.Predicate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//public class AccountTransferRequest {
//
//    @NonNull
//    private Integer sourceAccountId; // 转账人
//
//    @NonNull
//    private Integer targetAccountId; // 收款人
//
//    @NonNull
//    private Double amount; // 金额
//
//    public static Specification<Account> buildSource(AccountTransferRequest queryRequest) {
//        return (root, cquery, cbuild) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (queryRequest.getSourceAccountId() != null){
//                predicates.add(cbuild.equal(root.get("id"), queryRequest.getSourceAccountId()));
//            }
//            if (queryRequest.getAmount() != null){
//                predicates.add(cbuild.greaterThanOrEqualTo(root.get("amount"),(queryRequest.getAmount())));
//            }
//
//            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
//            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
//        };
//    }
//
//}
