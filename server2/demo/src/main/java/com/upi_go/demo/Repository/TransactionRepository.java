package com.upi_go.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upi_go.demo.Entities.TransactionEntity;

@Repository 
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    
}
