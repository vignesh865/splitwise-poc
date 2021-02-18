package com.setu.assignment.splitwisepocassignment.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTransactionRequest {

    private Set<String> participants = new HashSet<>();
    private Integer totalAmount = 0;
    private String payer;
    private SplitType type;
    private Map<String, Integer> amountSplit = new HashMap<>();

}
