package com.setu.assignment.splitwisepocassignment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.setu.assignment.splitwisepocassignment.dao.TransactionDataManager;
import com.setu.assignment.splitwisepocassignment.dao.TransactionDataManagerImpl;
import com.setu.assignment.splitwisepocassignment.dao.TransactionHistoryRepository;
import com.setu.assignment.splitwisepocassignment.dao.TransactionRepository;

@Configuration
public class AppConfigurations {

    @Bean
    public TransactionDataManager transactionDataManager(TransactionRepository repository, TransactionHistoryRepository historyRepository) {
        return new TransactionDataManagerImpl(repository, historyRepository);
    }

}
