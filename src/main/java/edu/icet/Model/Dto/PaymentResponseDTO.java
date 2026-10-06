package edu.icet.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {

    private Long paymentId;

    private String customerName;

    private String vehicleModel;

    private String vehicleImage;

    private LocalDateTime paidDate;

    private double amount;

    private String type;

    private String status;
}