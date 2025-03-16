package com.akhil.trading.repo;

import com.akhil.trading.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepo extends JpaRepository<ForgotPasswordToken,String> {
    Optional<ForgotPasswordToken> findByUserId(Long userId);
}
