package edu.icet.Repository;

import edu.icet.Model.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByPaymentStatus(String unpaid);

    List<Booking> findByUser_name(String userName);

    List<Booking> findByUser_userId(Long userId);

    List<Booking> findByStatus(String status);

    List<Booking> findByStatusIn(List<String> statuses);
}
