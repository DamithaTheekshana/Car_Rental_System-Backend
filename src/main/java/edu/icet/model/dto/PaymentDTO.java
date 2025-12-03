package edu.icet.model.dto;

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

    private Long bookingId;
}
