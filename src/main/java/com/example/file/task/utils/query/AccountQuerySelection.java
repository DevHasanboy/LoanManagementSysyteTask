package com.example.file.task.utils.query;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

import java.util.List;

public class AccountQuerySelection {

    public static List<Selection<?>> getAccountSelection(Root<?> root) {
        return List.of(
                root.get("id"),
                root.get("client").get("id"),
                root.get("accountType")
        );
    }

    public static List<Selection<?>> getUserJoinSelection(Join<?, ?> join) {
        return List.of(
                join.get("id"),
                join.get("firstName"),
                join.get("lastName")
        );
    }

}
