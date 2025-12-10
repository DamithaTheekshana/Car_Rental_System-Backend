package edu.icet.Model.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateBookingStatusDto {
    private Long bookingId;
    private String status;
}
