package edu.icet.Model.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingHistoryDto {

    private Long bookingId;
    private Long userId;
    private Long vehicleId;
    private LocalDate startDate;
    private LocalDate endDate;

    private String paymentStatus;
    private double paidAmount;

    private LocalDate completedDate;
}
