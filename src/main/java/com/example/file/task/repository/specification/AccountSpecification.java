package com.example.file.task.repository.specification;

import com.example.file.task.entity.Accounts;
import com.example.file.task.filter.AccountFilter;
import com.example.file.task.utils.query.AccountQuerySelection;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AccountSpecification implements Specification<Accounts> {
    private final AccountFilter filter;

    public AccountSpecification(AccountFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Accounts> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<Object, Object> joinUser = root.join("client", JoinType.LEFT);

        List<Selection<?>> selections = new ArrayList<>();
        selections.addAll(AccountQuerySelection.getUserJoinSelection(joinUser));
        selections.addAll(AccountQuerySelection.getAccountSelection(root));

        query.distinct(true);
        query.multiselect(selections.toArray(new Selection[0]));

        // Filtrlarni yig‘amiz
        List<Predicate> predicates = new ArrayList<>();
        if (filter.id() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), filter.id()));
        }
        if (filter.clientId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("client").get("id"), filter.clientId()));
        }
        if (filter.accountType() != null) {
            predicates.add(criteriaBuilder.equal(root.get("accountType"), filter.accountType()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
