package com.btplanner.btripexbackend.datamodel.repository;

import java.util.List;
import com.btplanner.btripexbackend.datamodel.accountmodel.Trip;
import com.btplanner.btripexbackend.datamodel.accountmodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

	@Query("select new Trip(t.id, t.name, t.destination, t.startDate, t.endDate," +
			" t.description, t.thumbnail, t.user) from Trip t where t.user = ?1 order by t.startDate")
	List<Trip> findAllByUser(User user);

	@Query("select t.id, t.name, t.thumbnail, t.user from Trip t where t.user = ?1")
	List<Trip> findTripByUser(User user);
}
