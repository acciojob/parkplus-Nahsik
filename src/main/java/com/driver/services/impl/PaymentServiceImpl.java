package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        if(!PaymentMode.isValidEnum(mode.toUpperCase())){
            throw new Exception("Payment mode not detected");
        }
          Payment payment = new Payment();
          payment.setReservation(reservationRepository2.findById(reservationId).get());
          payment.setPaymentMode(PaymentMode.valueOf(mode.toUpperCase()));
          payment.setPaymentCompleted(true);
          paymentRepository2.save(payment);
          return payment;
    }
}
