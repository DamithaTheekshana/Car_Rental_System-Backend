package edu.icet.controller;

import edu.icet.Model.Dto.PaymentDTO;
import edu.icet.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.icet.Model.Dto.PaymentResponseDTO;

import java.util.List;

@CrossOrigin(origins = "https://frontend-car-rental-system.vercel.app")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/addPayment")
    public void addPayment(@RequestBody PaymentDTO dto){
        paymentService.addPayment(dto);
    }

    @GetMapping("/all")
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/customer/{userId}")
    public List<PaymentResponseDTO> getPaymentsByCustomer(
            @PathVariable Long userId
    ) {
        return paymentService.getPaymentsByCustomer(userId);
    }
}
