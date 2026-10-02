package edu.icet.Service;

import edu.icet.Model.Dto.ReportSummaryDto;
import edu.icet.Model.Entity.BookingHistory;
import edu.icet.Model.Entity.Payment;
import edu.icet.Model.Entity.Users;
import edu.icet.Repository.BookingHistoryRepository;
import edu.icet.Repository.BookingRepository;
import edu.icet.Repository.PaymentRepository;
import edu.icet.Repository.UserRepository;
import edu.icet.Repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;
    private final BookingHistoryRepository bookingHistoryRepository;
    private final PaymentRepository paymentRepository;

    public ReportSummaryDto getReportSummary() {

        // Total customers
        long totalCustomers =
                userRepository.findByRole(Users.Role.CUSTOMER).size();

        // Total vehicles
        long totalVehicles = vehicleRepository.count();

        // Current bookings
        long currentBookings = bookingRepository.count();

        // Booking history
        List<BookingHistory> historyList =
                bookingHistoryRepository.findAll();

        // Total bookings = current + history
        long totalBookings =
                currentBookings + historyList.size();

        // Completed bookings only
        long completedBookings = historyList.stream()
                .filter(history ->
                        "COMPLETED".equalsIgnoreCase(history.getStatus()))
                .count();

        // Total revenue from PAID payments
        List<Payment> payments = paymentRepository.findAll();

        double totalRevenue = payments.stream()
                .filter(payment ->
                        "PAID".equalsIgnoreCase(payment.getStatus()))
                .mapToDouble(Payment::getAmount)
                .sum();

        return new ReportSummaryDto(
                totalCustomers,
                totalVehicles,
                totalBookings,
                completedBookings,
                totalRevenue
        );
    }
}