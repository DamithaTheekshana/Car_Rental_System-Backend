package edu.icet.controller;

import edu.icet.Model.Dto.BookingHistoryResponseDto;
import edu.icet.Service.BookingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking-history")
@CrossOrigin
public class BookingHistoryController {

    @Autowired
    private BookingHistoryService bookingHistoryService;

    @GetMapping("/customer/{customerId}")
    public List<BookingHistoryResponseDto> getHistoryByCustomerId(
            @PathVariable Long customerId
    ) {
        return bookingHistoryService.getHistoryByCustomerId(customerId);
    }
}