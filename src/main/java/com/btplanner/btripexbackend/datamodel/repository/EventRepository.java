package com.btplanner.btripexbackend.datamodel.repository;

import com.btplanner.btripexbackend.datamodel.accountmodel.Event;
import com.btplanner.btripexbackend.datamodel.accountmodel.Trip;
import org.hibernate.annotations.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByTrip(Trip trip);

    List<Event> findAllByTripOrderByStartDate(Trip trip);

}
