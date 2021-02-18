package com.setu.assignment.splitwisepocassignment.models;

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
public class Transaction {

    private String party1;
    private String party2;

    private int amount;
    private PartyRelation relation;

    public boolean isEqual(Transaction transaction) {
        return party1.equals(transaction.getParty1()) && party2.equals(transaction.getParty2());
    }

    public Transaction inverse() {
        Transaction transaction = new Transaction();
        transaction.setParty1(party2);
        transaction.setParty2(party1);
        transaction.setRelation(relation == PartyRelation.BORROWED_FROM ? PartyRelation.PAID_FOR : PartyRelation.BORROWED_FROM);
        transaction.setAmount(amount);
        return transaction;
    }

}
