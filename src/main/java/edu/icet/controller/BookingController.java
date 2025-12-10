package edu.icet.controller;

import edu.icet.Model.Dto.BookingDTO;
import edu.icet.Model.Dto.BookingResponseDto;
import edu.icet.Model.Dto.UpdateBookingStatusDto;
import edu.icet.Model.Entity.Booking;
import edu.icet.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")

public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/addBooking")
    public void addBooking(@RequestBody BookingDTO dto){
        System.out.println("bookingContoller dto :"+dto);
           bookingService.addBooking(dto);
    }

    @GetMapping("/allBookings")
    public List<BookingResponseDto> getAllBooking(){
        return bookingService.getAllBooking();
    }

    @DeleteMapping("deleteBooking/{bookingId}")
    public void deleteBooking(@PathVariable Long bookingId){
        bookingService.deleteBooking(bookingId);
    }

    @PutMapping("/updateBooking")
    public void updateBooking(@RequestBody BookingDTO dto){
        bookingService.updateBooking(dto);
    }

    @PutMapping("/update-status")
    public void updateStatus(@RequestBody UpdateBookingStatusDto dto){
        bookingService.updateStatus(dto);
    }
}
