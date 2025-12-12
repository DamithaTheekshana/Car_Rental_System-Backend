package edu.icet.Model.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingHistoryDTO {

    private Long bookingId;
    private Long customerId;
    private Long vehicleId;

    private LocalDateTime bookingDate;
    private LocalDate startDate;
    private LocalDate endDate;

    private double total;
    private String status;
}
