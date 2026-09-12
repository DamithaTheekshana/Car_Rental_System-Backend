package edu.icet.Service;

import edu.icet.Model.Entity.Booking;
import edu.icet.Model.Entity.BookingHistory;
import edu.icet.Repository.BookingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import edu.icet.Model.Dto.BookingHistoryResponseDto;
import edu.icet.Model.Entity.Vehicle;
import edu.icet.Repository.VehicleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingHistoryService {

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

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

    public List<BookingHistoryResponseDto> getHistoryByCustomerId(Long customerId) {

        List<BookingHistory> historyList =
                bookingHistoryRepository.findByCustomerId(customerId);

        return historyList.stream().map(history -> {

            BookingHistoryResponseDto dto =
                    new BookingHistoryResponseDto();

            dto.setHistoryId(history.getHistoryId());
            dto.setBookingId(history.getBookingId());
            dto.setBookingDate(history.getBookingDate());
            dto.setStartDate(history.getStartDate());
            dto.setEndDate(history.getEndDate());
            dto.setTotal(history.getTotal());
            dto.setStatus(history.getStatus());

            Vehicle vehicle = vehicleRepository
                    .findById(history.getVehicleId())
                    .orElse(null);

            if (vehicle != null) {
                dto.setVehicleModel(vehicle.getModel());
                dto.setVehicleImage(vehicle.getImagePath());
            }

            return dto;

        }).collect(Collectors.toList());
    }
}