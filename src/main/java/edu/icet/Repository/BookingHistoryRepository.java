package edu.icet.Repository;

import edu.icet.Model.Entity.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {
}
