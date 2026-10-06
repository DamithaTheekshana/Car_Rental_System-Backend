package edu.icet.Service;

import edu.icet.Model.Dto.PaymentDTO;
import edu.icet.Model.Entity.Booking;
import edu.icet.Model.Entity.Payment;
import edu.icet.Model.Entity.Vehicle;
import edu.icet.Repository.BookingRepository;
import edu.icet.Repository.PaymentRepository;
import edu.icet.Repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.icet.Model.Dto.PaymentResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingHistoryService bookingHistoryService;

    private ModelMapper mapper = new ModelMapper();

    @Transactional
    public void addPayment(PaymentDTO dto) {
        // 1. Fetch booking
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // 2. Save payment
        Payment payment = new Payment();
        mapper.map(dto, payment);
        payment.setAmount(dto.getAmount());
        payment.setType(dto.getType());
        payment.setStatus("PAID");
        payment.setPaidDate(LocalDateTime.now());
        payment.setBookingEntity(booking);

        paymentRepository.save(payment);

        // 3. Update booking payment status
        booking.setPaymentStatus("PAID");
        booking.setStatus("SUCCESS");
        bookingRepository.save(booking);

        // 4. Save booking to history
        bookingHistoryService.saveBookingHistory(booking, "COMPLETED");

        // 5. Make vehicle available
        Vehicle vehicle = booking.getVehicle();
        vehicle.setStatus("AVAILABLE");
        vehicleRepository.save(vehicle);

    }

    public List<PaymentResponseDTO> getAllPayments() {

        List<Payment> payments = paymentRepository.findAll();

        return payments.stream().map(payment -> {

            PaymentResponseDTO dto = new PaymentResponseDTO();

            dto.setPaymentId(payment.getPaidId());

            dto.setPaidDate(payment.getPaidDate());

            dto.setAmount(payment.getAmount());

            dto.setType(payment.getType());

            dto.setStatus(payment.getStatus());


            // Get booking details
            Booking booking = payment.getBookingEntity();

            if (booking != null) {

                // Customer
                if (booking.getUser() != null) {
                    dto.setCustomerName(
                            booking.getUser().getName()
                    );
                }

                // Vehicle
                if (booking.getVehicle() != null) {

                    dto.setVehicleModel(
                            booking.getVehicle().getModel()
                    );

                    dto.setVehicleImage(
                            booking.getVehicle().getImagePath()
                    );
                }
            }

            return dto;

        }).collect(Collectors.toList());
    }

    public List<PaymentResponseDTO> getPaymentsByCustomer(Long userId) {

        List<Payment> payments =
                paymentRepository.findByBookingEntity_User_UserId(userId);

        return payments.stream().map(payment -> {

            PaymentResponseDTO dto = new PaymentResponseDTO();

            dto.setPaymentId(payment.getPaidId());
            dto.setPaidDate(payment.getPaidDate());
            dto.setAmount(payment.getAmount());
            dto.setType(payment.getType());
            dto.setStatus(payment.getStatus());

            Booking booking = payment.getBookingEntity();

            if (booking != null) {

                if (booking.getUser() != null) {
                    dto.setCustomerName(
                            booking.getUser().getName()
                    );
                }

                if (booking.getVehicle() != null) {

                    dto.setVehicleModel(
                            booking.getVehicle().getModel()
                    );

                    dto.setVehicleImage(
                            booking.getVehicle().getImagePath()
                    );
                }
            }

            return dto;

        }).collect(Collectors.toList());
    }
}
