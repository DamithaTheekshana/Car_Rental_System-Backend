package edu.icet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paidId;

    private double amount;
    private LocalDateTime paidDate;
    private String status;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking bookingEntity;
}
