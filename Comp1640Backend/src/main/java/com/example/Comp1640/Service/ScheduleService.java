package com.example.Comp1640.Service;

import com.example.Comp1640.DTO.ScheduleDto;
import com.example.Comp1640.Entity.Schedule;

import java.util.List;

public interface ScheduleService {

    ScheduleDto saveSchedule(ScheduleDto scheduleDto,int weakNumber) throws Exception;
    List<ScheduleDto> getStudentSchedules(Long studentId);
    List<ScheduleDto> getTutorSchedules(Long tutorId);
    List<ScheduleDto> getSchedulesAll();

    ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDto);
    void deleteSchedule(Long id);

}
