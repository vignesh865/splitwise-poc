package com.setu.assignment.splitwisepocassignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.setu.assignment.splitwisepocassignment.dao.TransactionDataManager;
import com.setu.assignment.splitwisepocassignment.models.PartyRelation;
import com.setu.assignment.splitwisepocassignment.models.Transaction;
import com.setu.assignment.splitwisepocassignment.models.TransactionSummary;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final TransactionDataManager transactionDataManager;

    @Override
    public TransactionSummary getSummary(String party) {

        List<Transaction> transactions = sanitizeTransactions(transactionDataManager.getOnGoingTransactions(party), party);
        Map<PartyRelation, List<Transaction>> relationMap = transactions.stream().collect(Collectors.groupingBy(Transaction::getRelation));

        TransactionSummary summary = new TransactionSummary();
        summary.setTransactions(transactions);

        List<Transaction> borrowedFromTransactions = relationMap.getOrDefault(PartyRelation.BORROWED_FROM, new ArrayList<>());
        summary.setDebtTotal(borrowedFromTransactions.stream().mapToDouble(Transaction::getAmount).sum());

        List<Transaction> paidForTransactions = relationMap.getOrDefault(PartyRelation.PAID_FOR, new ArrayList<>());
        summary.setLendTotal(paidForTransactions.stream().mapToDouble(Transaction::getAmount).sum());

        return summary;
    }

    private List<Transaction> sanitizeTransactions(List<Transaction> transactions, String party) {

        return transactions.stream().map(transaction -> {

            if (transaction.getParty1().equals(party)) {
                return transaction;
            }
            return transaction.inverse();

        }).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getHistory(String party, int limit) {
        return transactionDataManager.getHistory(party, limit);
    }

}
