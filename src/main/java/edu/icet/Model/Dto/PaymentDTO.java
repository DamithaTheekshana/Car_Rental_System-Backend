package edu.icet.Model.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDTO {

    private Long paidId;
    private double amount;
    private LocalDateTime paidDate;
    private String status;
    private Long bookingId;
}
