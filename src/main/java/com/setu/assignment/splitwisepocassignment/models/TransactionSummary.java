package com.setu.assignment.splitwisepocassignment.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionSummary {

    private double debtTotal;
    private double lendTotal;

    private List<Transaction> transactions;

}
