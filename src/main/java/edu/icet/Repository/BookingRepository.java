package edu.icet.Repository;

import edu.icet.Model.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.vehicle.vehicleId = :vehicleId " +
            "AND b.startDate <= :endDate AND b.endDate >= :startDate")
    List<Booking> checkOverlap(Long vehicleId, LocalDate startDate, LocalDate endDate);

}
