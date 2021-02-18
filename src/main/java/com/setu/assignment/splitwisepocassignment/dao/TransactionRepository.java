package com.setu.assignment.splitwisepocassignment.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionDao, Parties> {

    @Query(value = "select * from transaction where party1 in (:parties) and party2 in (:parties)", nativeQuery = true)
    List<TransactionDao> getTransactions(@Param("parties") Set<String> parties);

    @Query(value = "select * from transaction where (party1=:party or party2=:party) and amount!=0", nativeQuery = true)
    List<TransactionDao> getOngoingTransactions(@Param("party") String party);

}
