package edu.icet.Model.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingResponseDto {

    private Long bookingId;
    private String vehicleModel;
    private String vehicleImage;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalDays;
    private String customerName;
    private String status;
    private double totalAmount;
    private String paymentStatus;
    private double dailyRate;
}
