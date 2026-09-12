package edu.icet.Model.Dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingHistoryResponseDto {

    private Long historyId;
    private Long bookingId;

    private String vehicleModel;
    private String vehicleImage;

    private LocalDateTime bookingDate;
    private LocalDate startDate;
    private LocalDate endDate;

    private double total;
    private String status;
}