package com.upi_go.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.upi_go.demo.Entities.BankAccountEntity;
import java.util.Optional;
@Repository 
public interface BankRepository extends JpaRepository<BankAccountEntity , Long> {
    Optional<BankAccountEntity> findByAccountNumber(String accountNumber);
}
