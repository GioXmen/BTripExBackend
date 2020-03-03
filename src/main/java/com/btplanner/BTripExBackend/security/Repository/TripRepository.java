package com.btplanner.BTripExBackend.security.Repository;

import com.btplanner.BTripExBackend.security.AccountModel.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
