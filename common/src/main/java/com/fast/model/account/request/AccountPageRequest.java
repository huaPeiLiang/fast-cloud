//package com.fast.model.account.request;
//
//import com.fast.model.BasePageRequest;
//import com.fast.model.account.root.Account;
//import lombok.Data;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.data.jpa.domain.Specification;
//
//import javax.persistence.criteria.Predicate;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//public class AccountPageRequest extends BasePageRequest implements Serializable {
//
//    private static final long serialVersionUID = 5935996018098628799L;
//
//    private String name;
//
//    /**
//     * 拼接分页参数
//     * */
//    public Specification<Account> build() {
//        return (root, cquery, cbuild) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            // 模糊查询 - 名称
//            if (StringUtils.isNotEmpty(name)) {
//                predicates.add(cbuild.like(root.get("name"), "%"+name+"%"));
//            }
//
//            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
//            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
//        };
//    }
//
//}
