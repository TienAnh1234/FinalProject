package com.example.Comp1640.Controller;


import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.ScheduleDto;
import com.example.Comp1640.Service.ScheduleService;
import com.example.Comp1640.Service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;


    @GetMapping()
    public ResponseEntity<List<ScheduleDto>> getAllScheduleDtoOfUser() {
        return ResponseEntity.ok(scheduleService.getSchedulesAll());
    }

    @PostMapping("/book_schedule")
    public ResponseEntity<ScheduleDto> saveNew(@RequestBody(required = false) ScheduleDto scheduleDto,@RequestParam int weakNumber) throws Exception {

        scheduleDto.setStartTime(scheduleDto.getStartTime().plusHours(7));
        scheduleDto.setEndTime(scheduleDto.getEndTime().plusHours(7));
        System.out.println("weakNumber: "+ weakNumber);
        return ResponseEntity.ok(scheduleService.saveSchedule(scheduleDto,weakNumber));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ScheduleDto>> getAllScheduleDto(@PathVariable Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
        if(Objects.equals(role, "ROLE_TUTOR")) {
            return ResponseEntity.ok(scheduleService.getTutorSchedules(userId));
        }else{
            return ResponseEntity.ok(scheduleService.getStudentSchedules(userId));
        }
    }

    @GetMapping("/student/{userId}")
    public ResponseEntity<List<ScheduleDto>> getAllStudentScheduleDto(@PathVariable Long userId) {
        return ResponseEntity.ok(scheduleService.getStudentSchedules(userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ScheduleDto> update(@PathVariable Long id,@RequestBody(required = false) ScheduleDto scheduleDto ) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, scheduleDto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        System.out.println(id + " is deleted");
        scheduleService.deleteSchedule(id);
    }



}
