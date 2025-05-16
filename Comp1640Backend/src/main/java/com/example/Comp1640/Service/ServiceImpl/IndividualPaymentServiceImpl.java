package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.DTO.GradeDto;
import com.example.Comp1640.DTO.IndividualPaymentDto;
import com.example.Comp1640.Entity.Grade;
import com.example.Comp1640.Entity.IndividualPayment;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Repository.GradeRepository;
import com.example.Comp1640.Repository.IndividualPaymentRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Service.IndividualPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndividualPaymentServiceImpl implements IndividualPaymentService {

    @Autowired
    private IndividualPaymentRepository individualPaymentRepository;

    @Autowired
    private TutorRepository tutorRepository;


    @Override
    public List<IndividualPaymentDto> getAllIndividualPaymentDto() {
        List<IndividualPayment> individualPayments = individualPaymentRepository.findAll();
        List<IndividualPaymentDto> individualPaymentDtos = new ArrayList<>();

        if (!individualPayments.isEmpty()) {
            for(int i = 0; i < individualPayments.size(); i++){
                IndividualPaymentDto individualPaymentDto = new IndividualPaymentDto(individualPayments.get(i).getId(), individualPayments.get(i).getAmount(), individualPayments.get(i).getTutor().getId(), individualPayments.get(i).getStartTime(), individualPayments.get(i).getEndTime());
                individualPaymentDtos.add(individualPaymentDto);
            }
            return individualPaymentDtos;
        } else {
            return null;
        }
    }

    @Override
    public void deleteIndividualPaymentDto(Long id) {
        IndividualPayment individualPayment  = individualPaymentRepository.findById(id).get();
        Tutor tutor = individualPayment.getTutor();
        tutor.setStatus("WAITTING");
        individualPaymentRepository.deleteById(id);
    }

    @Scheduled(cron = "0 0 2 * * *",zone = "Asia/Ho_Chi_Minh") // mỗi ngày lúc 2h
    public void deleteExpiredPayment() {
        LocalDateTime now = LocalDateTime.now();
//        individualPaymentRepository.deleteExpired(now);
        Tutor tutor;
        List<IndividualPayment> individualPayments = individualPaymentRepository.findExpired(now);
        for(int i = 0; i < individualPayments.size(); i++){
            tutor = individualPayments.get(i).getTutor();
            tutor.setStatus("WAITTING");
            tutorRepository.save(tutor);
        }

    }

}
