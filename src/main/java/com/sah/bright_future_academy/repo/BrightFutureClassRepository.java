package com.sah.bright_future_academy.repo;

import com.sah.bright_future_academy.model.BrightFutureClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrightFutureClassRepository extends JpaRepository<BrightFutureClass, Integer> {
}
