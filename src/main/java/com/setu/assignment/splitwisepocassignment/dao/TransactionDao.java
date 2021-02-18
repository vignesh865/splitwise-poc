package com.setu.assignment.splitwisepocassignment.dao;

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

@IdClass(Parties.class)
@Entity(name = "transaction")
public class TransactionDao {

    @Id
    private String party1;
    @Id
    private String party2;

    private int amount;
    private String relation;

    public static TransactionDao populate(Transaction transaction) {
        return new TransactionDaoBuilder().party1(transaction.getParty1()).party2(transaction.getParty2()).amount(transaction.getAmount())
                .relation(transaction.getRelation().toString()).build();
    }

    public static Transaction extract(TransactionDao dao) {

        Transaction transaction = new Transaction();

        transaction.setParty1(dao.getParty1());
        transaction.setParty2(dao.getParty2());

        transaction.setAmount(dao.getAmount());
        transaction.setRelation(PartyRelation.valueOf(dao.getRelation()));

        return transaction;
    }

}
