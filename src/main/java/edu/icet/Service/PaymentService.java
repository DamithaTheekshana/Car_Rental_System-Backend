package edu.icet.Service;

import edu.icet.Model.Dto.BookingResponseDto;
import edu.icet.Model.Dto.PaymentDTO;
import edu.icet.Model.Entity.Booking;
import edu.icet.Model.Entity.Payment;
import edu.icet.Model.Entity.Vehicle;
import edu.icet.Repository.BookingRepository;
import edu.icet.Repository.PaymentRepository;
import edu.icet.Repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    private ModelMapper mapper = new ModelMapper();


    public void addPayment(PaymentDTO dto) {
        // 1. Fetch booking
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // 2. Save payment
        Payment payment = new Payment();
        mapper.map(dto, payment);
        payment.setAmount(dto.getAmount());
        payment.setType("Cash");
        payment.setStatus("PAID");
        payment.setPaidDate(LocalDateTime.now());
        payment.setBookingEntity(booking);

        paymentRepository.save(payment);

        // 3. Update vehicle status
        Vehicle vehicle = booking.getVehicle();
        vehicle.setStatus("AVAILABLE");
        vehicleRepository.save(vehicle);

        payment.setBookingEntity(null);
        paymentRepository.save(payment);

        bookingRepository.delete(booking);
    }
}
