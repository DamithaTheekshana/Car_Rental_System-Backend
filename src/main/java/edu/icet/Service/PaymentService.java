package edu.icet.Service;

import edu.icet.Model.Dto.PaymentDTO;import edu.icet.Repository.BookingRepository;
import edu.icet.Repository.PaymentRepository;
import edu.icet.Repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    private ModelMapper mapper = new ModelMapper();


    public void addPayment(PaymentDTO dto) {

    }
}
