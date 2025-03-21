package com.akhil.trading.repo;

import com.akhil.trading.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepo extends JpaRepository<VerificationCode,Long> {
     Optional<VerificationCode> findByUserId(Long userId);
}
