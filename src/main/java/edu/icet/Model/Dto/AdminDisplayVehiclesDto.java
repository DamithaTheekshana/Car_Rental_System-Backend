package edu.icet.Model.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminDisplayVehiclesDto {

    private Long vehicleId;
    private String imagePath;
    private String model;
    private String regNo;
    private String brand;
    private String type;
    private String fuelType;
    private int seat;
    private double dailyRate;
    private String status;
}
