package com.setu.assignment.splitwisepocassignment.dao;

import java.util.List;
import java.util.Set;

import com.setu.assignment.splitwisepocassignment.models.Transaction;

public interface TransactionDataManager {

    List<Transaction> getTransactions(Set<String> parties);

    void updateTransactions(List<Transaction> transactions);

    List<Transaction> getOnGoingTransactions(String party);

    List<Transaction> getHistory(String party, int limit);

}
