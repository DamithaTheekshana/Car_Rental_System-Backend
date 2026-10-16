package edu.icet.controller;

import edu.icet.Model.Dto.ReportSummaryDto;
import edu.icet.Service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://frontend-car-rental-system.vercel.app")
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/summary")
    public ReportSummaryDto getReportSummary() {
        return reportService.getReportSummary();
    }
}
