package com.setu.assignment.splitwisepocassignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.setu.assignment.splitwisepocassignment.models.CreateTransactionRequest;
import com.setu.assignment.splitwisepocassignment.models.Transaction;
import com.setu.assignment.splitwisepocassignment.models.TransactionSummary;
import com.setu.assignment.splitwisepocassignment.service.SummaryService;
import com.setu.assignment.splitwisepocassignment.service.TransactionService;

@RestController
public class ApplicationController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SummaryService summaryService;

    @PostMapping("/transactions")
    public void createTransactions(@RequestBody CreateTransactionRequest transactionRequest) {
        transactionService.createTransactions(transactionRequest);
    }

    @ResponseBody
    @GetMapping("/summary/{party}")
    public TransactionSummary getSummary(@PathVariable("party") String party) {
        return summaryService.getSummary(party);
    }

    @ResponseBody
    @GetMapping("/history/{party}")
    public List<Transaction> getHistory(@PathVariable("party") String party, @RequestParam("limit") int limit) {
        return summaryService.getHistory(party, limit);
    }

}
