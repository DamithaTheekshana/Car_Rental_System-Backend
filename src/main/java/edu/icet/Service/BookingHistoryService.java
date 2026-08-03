package edu.icet.Service;

import edu.icet.Model.Entity.Booking;
import edu.icet.Model.Entity.BookingHistory;
import edu.icet.Repository.BookingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class BookingHistoryService {

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    public void saveBookingHistory(Booking booking, String status) {

        BookingHistory history = new BookingHistory();

        history.setBookingId(booking.getBookingId());
        history.setCustomerId(booking.getUser().getUserId());
        history.setVehicleId(booking.getVehicle().getVehicleId());

        history.setBookingDate(booking.getBookingDate());
        history.setStartDate(booking.getStartDate());
        history.setEndDate(booking.getEndDate());

        long days = ChronoUnit.DAYS.between(
                booking.getStartDate(),
                booking.getEndDate()
        ) + 1;

        double total = days * booking.getVehicle().getDailyRate();

        history.setTotal(total);
        history.setStatus(status);

        bookingHistoryRepository.save(history);
    }
}