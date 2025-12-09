package edu.icet.Service;

import edu.icet.Model.Dto.BookingDTO;
import edu.icet.Model.Dto.BookingResponseDto;
import edu.icet.Model.Entity.Booking;
import edu.icet.Model.Entity.Users;
import edu.icet.Model.Entity.Vehicle;
import edu.icet.Repository.BookingRepository;
import edu.icet.Repository.UserRepository;
import edu.icet.Repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    ModelMapper mapper = new ModelMapper();

    public Booking addBooking(BookingDTO dto) {

        Users user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId()).orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Booking booking = new Booking();
        booking.setBookingDate(dto.getBookingDate());
        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        booking.setStatus(dto.getStatus());

        booking.setUser(user);
        booking.setVehicle(vehicle);

        return bookingRepository.save(booking);
    }

    public List<BookingResponseDto> getAllBooking() {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map(b -> {
            BookingResponseDto dto = new BookingResponseDto();
            dto.setBookingId(b.getBookingId());
            dto.setVehicleModel(b.getVehicle().getModel());
            dto.setVehicleImage(b.getVehicle().getImagePath());
            dto.setStartDate(b.getStartDate());
            dto.setEndDate(b.getEndDate());

            int days = (int) ChronoUnit.DAYS.between(b.getStartDate(), b.getEndDate()) + 1;
            dto.setTotalDays(days);

            double totalAmount = days * b.getVehicle().getDailyRate();

            dto.setCustomerName(b.getUser().getName());
            dto.setStatus(b.getStatus());
            dto.setTotalAmount(totalAmount);
            dto.setPaymentStatus(b.getPayment() != null ? b.getPayment().getStatus() : "PENDING");
            dto.setDailyRate(b.getVehicle().getDailyRate()); // NEW

            return dto;
        }).collect(Collectors.toList());
    }

    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public void updateBooking(BookingDTO dto) {
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        mapper.map(dto, booking);
        bookingRepository.save(booking);
    }
}
