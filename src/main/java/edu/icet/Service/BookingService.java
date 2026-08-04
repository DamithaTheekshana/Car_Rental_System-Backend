package edu.icet.Service;

import edu.icet.Model.Dto.BookingDTO;
import edu.icet.Model.Dto.BookingResponseDto;
import edu.icet.Model.Dto.UpdateBookingStatusDto;
import edu.icet.Model.Entity.Booking;
import edu.icet.Model.Entity.Users;
import edu.icet.Model.Entity.Vehicle;
import edu.icet.Repository.BookingRepository;
import edu.icet.Repository.UserRepository;
import edu.icet.Repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    BookingHistoryService bookingHistoryService;

    ModelMapper mapper = new ModelMapper();

    public Booking addBooking(BookingDTO dto) {

        Users user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId()).orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Booking booking = new Booking();
        booking.setBookingDate(LocalDateTime.now());
        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());

        booking.setUser(user);
        booking.setVehicle(vehicle);

        return bookingRepository.save(booking);
    }

    public List<BookingResponseDto> getAllBooking() {
        List<Booking> bookings = bookingRepository.findByPaymentStatus("UNPAID");



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
            dto.setPaymentStatus(b.getPaymentStatus());
            dto.setDailyRate(b.getVehicle().getDailyRate()); // NEW

            return dto;
        }).collect(Collectors.toList());
    }

//    public void deleteBooking(Long bookingId) {
//
//        bookingRepository.deleteById(bookingId);
//    }
    public void deleteBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Save booking to history as CANCELLED
        bookingHistoryService.saveBookingHistory(booking, "CANCELLED");

        // Make vehicle available again
        Vehicle vehicle = booking.getVehicle();

        if ("BOOKED".equalsIgnoreCase(vehicle.getStatus())) {
            vehicle.setStatus("AVAILABLE");
            vehicleRepository.save(vehicle);
        }

        // Delete booking from booking table
        bookingRepository.deleteById(bookingId);
    }

    public void updateBooking(BookingDTO dto) {
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        mapper.map(dto, booking);
        bookingRepository.save(booking);
    }

    public void updateStatus(UpdateBookingStatusDto dto) {

        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking Not Found!"));

        String status = dto.getStatus();

        // ADMIN APPROVES BOOKING
        if ("APPROVED".equalsIgnoreCase(status)) {

            booking.setStatus("APPROVED");
            bookingRepository.save(booking);

            Vehicle vehicle = booking.getVehicle();
            vehicle.setStatus("BOOKED");
            vehicleRepository.save(vehicle);

        }

        // ADMIN REJECTS BOOKING
        else if ("REJECT".equalsIgnoreCase(status)) {

            // Save booking to history
            bookingHistoryService.saveBookingHistory(booking, "REJECTED");

            // Make vehicle available
            Vehicle vehicle = booking.getVehicle();
            vehicle.setStatus("AVAILABLE");
            vehicleRepository.save(vehicle);

            // Delete booking from booking table
            bookingRepository.deleteById(booking.getBookingId());
        }
    }

    public List<BookingResponseDto> searchBooking(String name) {

        List<Booking> bookings = bookingRepository.findByUser_name(name);


        if (bookings.isEmpty()) {
//            return Collections.emptyList();
            System.out.println("Your Name has No Bookings");
        }

        // Booking → BookingResponseDto convert කරලා map කරනවා
        return bookings.stream().map(booking -> {
            BookingResponseDto dto = new BookingResponseDto();
            dto.setBookingId(booking.getBookingId());
            dto.setVehicleModel(booking.getVehicle().getModel());
            dto.setVehicleImage(booking.getVehicle().getImagePath());
            dto.setStartDate(booking.getStartDate());
            dto.setEndDate(booking.getEndDate());

            return dto;
        }).collect(Collectors.toList());
    }
}
