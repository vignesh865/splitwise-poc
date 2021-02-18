package com.setu.assignment.splitwisepocassignment.service;

import java.util.List;

import com.setu.assignment.splitwisepocassignment.models.Transaction;
import com.setu.assignment.splitwisepocassignment.models.TransactionSummary;

public interface SummaryService {

    TransactionSummary getSummary(String party);

    List<Transaction> getHistory(String party, int limit);

}
