package edu.icet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Long customerId;
    private Long vehicleId;

    private LocalDateTime bookingDate;
    private LocalDate startDate;
    private LocalDate endDate;

    private double total;
    private String status;
}
