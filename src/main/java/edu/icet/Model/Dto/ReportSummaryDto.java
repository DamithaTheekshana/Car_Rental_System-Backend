package edu.icet.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportSummaryDto {

    private long totalCustomers;
    private long totalVehicles;
    private long totalBookings;
    private long completedBookings;
    private double totalRevenue;
}