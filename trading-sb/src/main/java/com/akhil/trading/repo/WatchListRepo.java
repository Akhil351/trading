package com.akhil.trading.repo;

import com.akhil.trading.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchListRepo extends JpaRepository<WatchList,Long> {
    Optional<WatchList> findByUserId(Long userId);
}
