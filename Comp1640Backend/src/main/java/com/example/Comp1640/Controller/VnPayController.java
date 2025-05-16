package com.example.Comp1640.Controller;


import com.example.Comp1640.Entity.*;
import com.example.Comp1640.Repository.IndividualPaymentRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Repository.UserRepository;
import com.example.Comp1640.Service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Objects;

@RestController
@RequestMapping("/api/vnpay")
@CrossOrigin(origins = "*")
public class VnPayController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IndividualPaymentRepository individualPaymentRepository;

    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest request, HttpServletRequest req) {
        try {
            String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
            String url = baseUrl + "/api/vnpay";
            String paymentUrl = vnPayService.createOrder(request.getAmount(), request.getOrderInfo(), url);
            return ResponseEntity.ok(Collections.singletonMap("paymentUrl", paymentUrl));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/vnpay-payment")
    public void  paymentReturn(HttpServletResponse response, HttpServletRequest request ) throws IOException {




        LocalDateTime startTime = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDateTime endTime;



        int paymentStatus =vnPayService.orderReturn(request);
        System.out.println("ans");

        System.out.println("paymentStatus:"+paymentStatus);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String[] parts = orderInfo.split(":");

        String orderInfomain = parts[0];
        String username = parts.length > 1 ? parts[1] : null;

        System.out.println("username:"+username);
        User user = userRepository.findByUsername(username).orElse(null);
        Tutor tutor = tutorRepository.findById(tutorRepository.findIdByUserId(user.getId())).get();
        tutor.setStatus("PRIME");
        tutorRepository.save(tutor);
        if(Objects.equals(orderInfomain, "1Month")){
            endTime = startTime.plusMonths(1);
        }else if(Objects.equals(orderInfomain, "3Month")){
            endTime = startTime.plusMonths(3);
        }else{
            endTime = startTime.plusMonths(6);
        }

        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

//
        String redirectUrl = "http://localhost:4200/user/vnpay-payment?orderInfo=" + orderInfomain +
                "&paymentTime=" + paymentTime +
                "&transactionId=" + transactionId +
                "&totalPrice=" + totalPrice;
//
//
        IndividualPayment individualPayment = new IndividualPayment();
        individualPayment.setAmount(totalPrice);
        individualPayment.setTutor(tutor);
        individualPayment.setStartTime(startTime);
        individualPayment.setEndTime(endTime);
        individualPaymentRepository.save(individualPayment);

        response.sendRedirect(redirectUrl);
    }


}
