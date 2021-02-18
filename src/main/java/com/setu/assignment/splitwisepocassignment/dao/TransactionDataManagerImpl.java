package com.setu.assignment.splitwisepocassignment.dao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.setu.assignment.splitwisepocassignment.models.Transaction;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionDataManagerImpl implements TransactionDataManager {

    private final TransactionRepository repository;
    private final TransactionHistoryRepository historyRepository;

    @Override
    public List<Transaction> getTransactions(Set<String> partyList) {
        List<TransactionDao> daos = repository.getTransactions(partyList);
        return daos.stream().map(TransactionDao::extract).collect(Collectors.toList());
    }

    @Override
    public void updateTransactions(List<Transaction> transactions) {

        List<TransactionDao> daos = transactions.stream().map(TransactionDao::populate).collect(Collectors.toList());
        repository.saveAll(daos);

        List<TransactionHistoryDao> historyDaos = transactions.stream().map(TransactionHistoryDao::populate).collect(Collectors.toList());
        historyRepository.saveAll(historyDaos);
    }

    @Override
    public List<Transaction> getOnGoingTransactions(String party) {
        List<TransactionDao> daos = repository.getOngoingTransactions(party);
        return daos.stream().map(TransactionDao::extract).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getHistory(String party, int limit) {
        List<TransactionHistoryDao> daos = historyRepository.getHistory(party, limit);
        return daos.stream().map(TransactionHistoryDao::extract).collect(Collectors.toList());
    }

}
