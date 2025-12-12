package edu.icet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private LocalDateTime bookingDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status = "PENDING";
    @Column(name = "payment_status")
    private String paymentStatus = "UNPAID";


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;


    @OneToOne(mappedBy = "bookingEntity", cascade = CascadeType.ALL)
    private Payment payment;

}
