package com.setu.assignment.splitwisepocassignment.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.setu.assignment.splitwisepocassignment.dao.TransactionDataManager;
import com.setu.assignment.splitwisepocassignment.exceptions.CreateTransactionException;
import com.setu.assignment.splitwisepocassignment.models.CreateTransactionRequest;
import com.setu.assignment.splitwisepocassignment.models.PartyRelation;
import com.setu.assignment.splitwisepocassignment.models.SplitType;
import com.setu.assignment.splitwisepocassignment.models.Transaction;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDataManager transactionDataManager;

    @Override
    public void createTransactions(CreateTransactionRequest transactionRequest) {

        validateRequest(transactionRequest);
        sanitizeAmountSplitup(transactionRequest);

        List<Transaction> transactions = transactionDataManager.getTransactions(transactionRequest.getParticipants());

        modifyTransactions(transactions, transactionRequest);
        transactionDataManager.updateTransactions(transactions);
    }

    private void validateRequest(CreateTransactionRequest transactionRequest) {

        if (transactionRequest.getParticipants().size() < 2) {
            throw new CreateTransactionException("Minimum 2 Participants needed");
        }

        if (transactionRequest.getTotalAmount() <= 0) {
            throw new CreateTransactionException("Invalid amount");
        }

        if (transactionRequest.getType() == SplitType.EQUALLY) {
            return;
        }

        boolean isValidParticipants = transactionRequest.getAmountSplit().keySet().containsAll(transactionRequest.getParticipants());
        if (!isValidParticipants) {
            throw new CreateTransactionException("Invalid participants in split up");
        }

        int splitUpAmount = transactionRequest.getAmountSplit().values().stream().mapToInt(Integer::intValue).sum();
        if (transactionRequest.getTotalAmount() != splitUpAmount) {
            throw new CreateTransactionException("Amount split up should be matched with total amount");
        }

    }

    private void sanitizeAmountSplitup(CreateTransactionRequest transactionRequest) {

        if (transactionRequest.getType() != SplitType.EQUALLY) {
            return;
        }

        int splitupAmount = transactionRequest.getTotalAmount() / transactionRequest.getParticipants().size();
        transactionRequest.getParticipants().forEach(participant -> transactionRequest.getAmountSplit().put(participant, splitupAmount));
    }

    private void modifyTransactions(List<Transaction> currentTransactions, CreateTransactionRequest transactionRequest) {

        String payer = transactionRequest.getPayer();
        Map<String, Integer> amountSplit = transactionRequest.getAmountSplit();
        amountSplit.remove(transactionRequest.getPayer());

        Map<PartyRelation, List<Transaction>> filteredTransactions = currentTransactions.stream()
                .filter(transaction -> transaction.getParty1().equals(payer) || transaction.getParty2().equals(payer))
                .collect(Collectors.groupingBy(Transaction::getRelation));

        for (Transaction transaction : filteredTransactions.getOrDefault(PartyRelation.PAID_FOR, new ArrayList<>())) {
            mergePaidForTransaction(transaction, amountSplit, payer);
        }

        for (Transaction transaction : filteredTransactions.getOrDefault(PartyRelation.BORROWED_FROM, new ArrayList<>())) {
            mergeBorrowedFromTransaction(transaction, amountSplit, payer);
        }

        appendFreshTransactions(currentTransactions, amountSplit, transactionRequest.getPayer());
    }

    private void mergePaidForTransaction(Transaction transaction, Map<String, Integer> amountSplit, String payer) {

        if (transaction.getParty1().equals(payer)) {
            transaction.setAmount(transaction.getAmount() + amountSplit.getOrDefault(transaction.getParty2(), 0));
        }

        if (transaction.getParty2().equals(payer)) {
            int amount = transaction.getAmount() - amountSplit.getOrDefault(transaction.getParty1(), 0);
            transaction.setRelation(amount < 0 ? PartyRelation.BORROWED_FROM : PartyRelation.PAID_FOR);
            transaction.setAmount(Math.abs(amount));
        }
    }

    private void mergeBorrowedFromTransaction(Transaction transaction, Map<String, Integer> amountSplit, String payer) {

        if (transaction.getParty1().equals(payer)) {
            int amount = transaction.getAmount() - amountSplit.getOrDefault(transaction.getParty2(), 0);
            transaction.setRelation(amount > 0 ? PartyRelation.BORROWED_FROM : PartyRelation.PAID_FOR);
            transaction.setAmount(Math.abs(amount));
        }

        if (transaction.getParty2().equals(payer)) {
            transaction.setAmount(transaction.getAmount() + amountSplit.getOrDefault(transaction.getParty1(), 0));
        }
    }

    private void appendFreshTransactions(List<Transaction> currentTransactions, Map<String, Integer> amountSplit, String payer) {

        Set<String> involvedParties = new HashSet<>();

        currentTransactions.forEach(currentTransaction -> {
            involvedParties.add(currentTransaction.getParty1());
            involvedParties.add(currentTransaction.getParty2());
        });

        if (involvedParties.contains(payer)) {
            involvedParties.forEach(amountSplit::remove);
        }

        if (amountSplit.isEmpty()) {
            return;
        }

        currentTransactions.addAll(createTransactions(payer, amountSplit));
    }

    private List<Transaction> createTransactions(String payee, Map<String, Integer> amountSplit) {

        List<Transaction> transactions = new ArrayList<>();

        amountSplit.forEach((key, value) -> {

            if (!key.equals(payee)) {

                Transaction transaction = new Transaction();
                transaction.setParty1(payee);
                transaction.setParty2(key);
                transaction.setAmount(value);
                transaction.setRelation(PartyRelation.PAID_FOR);

                transactions.add(transaction);
            }
        });

        return transactions;
    }

}
