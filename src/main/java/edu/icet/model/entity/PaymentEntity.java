package edu.icet.model.entity;

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
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paidId;

    private double amount;
    private LocalDateTime paidDate;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity bookingEntity;
}
