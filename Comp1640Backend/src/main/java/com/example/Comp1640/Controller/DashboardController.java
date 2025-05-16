package com.example.Comp1640.Controller;

import com.example.Comp1640.DTO.Dashboard.AccountCountDto;
import com.example.Comp1640.DTO.Dashboard.BlogCountDto;
import com.example.Comp1640.DTO.Dashboard.MajorCountDto;
import com.example.Comp1640.DTO.MonthlyTotalDto;
import com.example.Comp1640.DTO.PopularSubjectProjection;
import com.example.Comp1640.Repository.*;
import com.example.Comp1640.Service.MajorgradeTutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private MajorgradeTutorService majorgradeTutorService;


    @Autowired
    private IndividualPaymentRepository individualPaymentRepository;

    @GetMapping("/new-accounts-total")
    public ResponseEntity<AccountCountDto> countTotalAccounts() {
        long students = studentRepository.count();
        long tutors = tutorRepository.count();

        AccountCountDto dto = new AccountCountDto(students, tutors);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/blogs-by-month")
    public ResponseEntity<List<BlogCountDto>> getBlogsByMonth() {
        List<BlogCountDto> results = blogRepository.countBlogsByMonthAllTime();
        return ResponseEntity.ok(results);
    }


    @GetMapping("/most-popular-subject")
    public List<PopularSubjectProjection> getMostPopularSubject() {
        return majorgradeTutorService.getMostPopularSubject();
    }

    @GetMapping("/monthly-totalAmount")
    public List<MonthlyTotalDto> getMonthlyTotalAmount() {
        List<MonthlyTotalDto> check = individualPaymentRepository.findTotalAmountByMonth();
//        System.out.println(check.size() + " houiiiiiii");
//
//        for(MonthlyTotalDto dto : check) {
//            System.out.println(dto.getTotalAmount() + " " + dto.getMonth());
//        }

        return individualPaymentRepository.findTotalAmountByMonth(); // hoặc gọi repository trực tiếp nếu dùng đơn giản
    }


}
