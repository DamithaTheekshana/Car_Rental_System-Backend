package edu.icet.Model.Dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDTO {

    private Long bookingId;
    private LocalDateTime bookingDate;
    private LocalDate startDate;
    private LocalDate endDate;

    private Long userId;
    private Long vehicleId;


}
