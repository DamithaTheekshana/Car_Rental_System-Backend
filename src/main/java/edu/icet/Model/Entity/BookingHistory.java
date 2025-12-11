package edu.icet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "booking_history")
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    private Long bookingId;
    private Long userId;
    private Long vehicleId;
    private LocalDate startDate;
    private LocalDate endDate;

    private String paymentStatus;
    private double paidAmount;

    private LocalDate completedDate;
}
