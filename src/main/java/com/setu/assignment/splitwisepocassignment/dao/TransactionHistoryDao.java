package com.setu.assignment.splitwisepocassignment.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.setu.assignment.splitwisepocassignment.models.PartyRelation;
import com.setu.assignment.splitwisepocassignment.models.Transaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@IdClass(PartiesHistory.class)
@Entity(name = "transaction_history")
public class TransactionHistoryDao {

    @Id
    private String party1;
    @Id
    private String party2;

    private int amount;
    private String relation;

    @Id
    private Date createdOn;

    public static TransactionHistoryDao populate(Transaction transaction) {
        return new TransactionHistoryDaoBuilder().party1(transaction.getParty1()).party2(transaction.getParty2()).amount(transaction.getAmount())
                .relation(transaction.getRelation().toString()).createdOn(new Date()).build();
    }

    public static Transaction extract(TransactionHistoryDao dao) {

        Transaction transaction = new Transaction();

        transaction.setParty1(dao.getParty1());
        transaction.setParty2(dao.getParty2());

        transaction.setAmount(dao.getAmount());
        transaction.setRelation(PartyRelation.valueOf(dao.getRelation()));

        return transaction;
    }

}
