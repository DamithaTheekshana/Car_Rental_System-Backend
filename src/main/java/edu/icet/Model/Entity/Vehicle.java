package edu.icet.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    private String imagePath;
    private String model;

    @Column(unique = true, nullable = false)
    private String regNo;

    private String brand;
    private String type;
    private String fuelType;
    private int seat;
    private double dailyRate;
    private String status;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<BookingEntity> bookings;
}
