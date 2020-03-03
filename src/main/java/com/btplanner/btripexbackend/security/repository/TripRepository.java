package com.btplanner.btripexbackend.security.repository;

import com.btplanner.btripexbackend.security.accountmodel.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
