package com.setu.assignment.splitwisepocassignment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends CrudRepository<TransactionHistoryDao, Parties> {

    @Query(value = "select * from transaction_history where party1=:party or party2=:party order by created_on desc limit :limit", nativeQuery = true)
    List<TransactionHistoryDao> getHistory(@Param("party") String party, @Param("limit") int limit);

}
