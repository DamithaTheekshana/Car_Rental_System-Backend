package edu.icet.Service;

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

    ModelMapper mapper = new ModelMapper();

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    public void addPayment(PaymentDTO dto) {
//        Payment payment = new Payment();
//        mapper.map(dto,Payment.class);
//        paymentRepository.save(payment);
//    }
        // 1. Find related booking
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // 2. Map DTO to Payment entity
        Payment payment = mapper.map(dto, Payment.class);

        // 3. Set paid date
        payment.setPaidDate(LocalDateTime.now());

        // 4. Set status = PAID
        payment.setStatus("PAID");

        // 5. Set booking to payment
        payment.setBookingEntity(booking);

        // 6. Save payment
        paymentRepository.save(payment);

        // 7. Update vehicle status to AVAILABLE
        Vehicle vehicle = booking.getVehicle();
        vehicle.setStatus("AVAILABLE");
        vehicleRepository.save(vehicle);
    }
}
