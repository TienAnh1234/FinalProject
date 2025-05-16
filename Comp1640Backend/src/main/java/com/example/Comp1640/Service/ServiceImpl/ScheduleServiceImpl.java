package com.example.Comp1640.Service.ServiceImpl;

import com.example.Comp1640.Config.GoogleCalendarConfig;
import com.example.Comp1640.DTO.MajorDto;
import com.example.Comp1640.DTO.ScheduleDto;
import com.example.Comp1640.DTO.StudentDto;
import com.example.Comp1640.DTO.TutorDto;
import com.example.Comp1640.Entity.Major;
import com.example.Comp1640.Entity.Schedule;
import com.example.Comp1640.Entity.Student;
import com.example.Comp1640.Entity.Tutor;
import com.example.Comp1640.Repository.ScheduleRepository;
import com.example.Comp1640.Repository.StudentRepository;
import com.example.Comp1640.Repository.TutorRepository;
import com.example.Comp1640.Service.ScheduleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.google.api.client.util.DateTime;

import org.springframework.stereotype.Service;

import java.util.UUID;



@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public ScheduleDto saveSchedule(ScheduleDto scheduleDto,int weakNumber) throws Exception {
        if(scheduleDto == null){
            return null;
        }else {
            Student student = studentRepository.findById(scheduleDto.getStudentDto().getId()).orElse(null);
            Tutor tutor = tutorRepository.findById(scheduleDto.getTutorDto().getId()).orElse(null);

            String dayOfWeek = scheduleDto.getStartTime().getDayOfWeek().toString();
            int year = scheduleDto.getStartTime().getYear();



            int weekOfYearDraft;
            if(Objects.equals(dayOfWeek, "SUNDAY")){
                weekOfYearDraft = scheduleDto.getStartTime().get(WeekFields.of(Locale.getDefault()).weekOfYear()) - 1;

            }else{
                weekOfYearDraft = scheduleDto.getStartTime().get(WeekFields.of(Locale.getDefault()).weekOfYear());

            }

            System.out.println(weekOfYearDraft);
            System.out.println(year);


            for(int i = 0 ; i < weakNumber ; i++){


                String weekOfYear = String.format("%d-W%02d", year, weekOfYearDraft + i);


                Optional<Schedule> optionalSchedule1 = scheduleRepository.findFirstMatchingScheduleStudent(
                        scheduleDto.getStartTime(), student.getId(), dayOfWeek, weekOfYear
                );

                Optional<Schedule> optionalSchedule2 = scheduleRepository.findFirstMatchingScheduleTutor(
                        scheduleDto.getStartTime(), tutor.getId(), dayOfWeek, weekOfYear
                );

                if(optionalSchedule1.isPresent() || optionalSchedule2.isPresent()){
                    System.out.println(12312313);
                    continue;
                }else{
                    System.out.println("tinndnjna");
                }

                String address;
                if(Objects.equals(scheduleDto.getScheduleFormat(), "ONLINE")){
                    address = createMeetLink(scheduleDto.getStartTime() + "",scheduleDto.getEndTime()+ "");
                }else{
                    address = scheduleDto.getAddress();
                }

//                String link = createMeetLink(scheduleDto.getStartTime() + "",scheduleDto.getEndTime()+ "");
                Schedule schedule = new Schedule(student,
                        tutor,
                        scheduleDto.getStartTime(),
                        scheduleDto.getEndTime(),
                        dayOfWeek,
                        weekOfYear,
                        scheduleDto.getStatus(),
                        scheduleDto.getScheduleFormat(),
                        address);
                scheduleRepository.save(schedule);
            }


            return scheduleDto;
        }
    }

    @Override
    public List<ScheduleDto> getStudentSchedules(Long studentId) {
        List<Schedule> schedules = scheduleRepository.findByStudentId(studentId);
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (schedules.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < schedules.size(); i++){
                ScheduleDto scheduleDto = new ScheduleDto(schedules.get(i).getId(),
                        new StudentDto(schedules.get(i).getStudent()),
                        new TutorDto(schedules.get(i).getTutor()),
                        schedules.get(i).getStartTime(),
                        schedules.get(i).getEndTime(),
                        schedules.get(i).getDayOfWeek(),
                        schedules.get(i).getWeekOfYear(),
                        schedules.get(i).getStatus(),
                        schedules.get(i).getScheduleFormat(),
                        schedules.get(i).getAddress());
                scheduleDtos.add(scheduleDto);
            }
        }


        return scheduleDtos;
    }



    @Override
    public List<ScheduleDto> getTutorSchedules(Long tutorId) {
        List<Schedule> schedules = scheduleRepository.findByTutorId(tutorId);
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (schedules.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < schedules.size(); i++){
                ScheduleDto scheduleDto = new ScheduleDto(schedules.get(i).getId(),
                        new StudentDto(schedules.get(i).getStudent()),
                        new TutorDto(schedules.get(i).getTutor()),
                        schedules.get(i).getStartTime(),
                        schedules.get(i).getEndTime(),
                        schedules.get(i).getDayOfWeek(),
                        schedules.get(i).getWeekOfYear(),
                        schedules.get(i).getStatus(),
                        schedules.get(i).getScheduleFormat(),
                        schedules.get(i).getAddress());
                scheduleDtos.add(scheduleDto);
            }
        }
        return scheduleDtos;
    }

    @Override
    public List<ScheduleDto> getSchedulesAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (schedules.isEmpty()) {
            return null;
        }else{
            for(int i = 0; i < schedules.size(); i++){
                ScheduleDto scheduleDto = new ScheduleDto(schedules.get(i).getId(),
                        new StudentDto(schedules.get(i).getStudent()),
                        new TutorDto(schedules.get(i).getTutor()),
                        schedules.get(i).getStartTime(),
                        schedules.get(i).getEndTime(),
                        schedules.get(i).getDayOfWeek(),
                        schedules.get(i).getWeekOfYear(),
                        schedules.get(i).getStatus(),
                        schedules.get(i).getScheduleFormat(),
                        schedules.get(i).getAddress());
                scheduleDtos.add(scheduleDto);
            }
        }
        return scheduleDtos;
    }

    @Override
    public ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDto) {
        try {
            Schedule existingSchedule = scheduleRepository.findById(id).orElse(null);
            existingSchedule.setStatus(scheduleDto.getStatus());
            scheduleRepository.save(existingSchedule);
            return scheduleDto;

        }
        catch(Exception e){
            return null;
        }
    }

    @Transactional
    @Override
    public void deleteSchedule(Long id) {
        System.out.println(id + " is deleteádadadd");
        try {
            scheduleRepository.deleteById(id);
            List<Schedule> schedules = scheduleRepository.findAll();
            System.out.println(schedules.size());

        } catch(Exception e ) {
            System.out.println(e);
        }
    }


    public String createMeetLink(String startTime, String endTime) throws Exception {
        Calendar service = GoogleCalendarConfig.getCalendarService();

        Event event = new Event()
                .setSummary("Meeting with tutor")
                .setDescription("Scheduled via app");

        DateTime startDateTime = convertToGoogleDateTime(startTime);
        EventDateTime start = new EventDateTime().setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = convertToGoogleDateTime(endTime);
        EventDateTime end = new EventDateTime().setDateTime(endDateTime);
        event.setEnd(end);

        // Enable Google Meet link
        ConferenceData conferenceData = new ConferenceData();
        CreateConferenceRequest conferenceRequest = new CreateConferenceRequest()
                .setRequestId(UUID.randomUUID().toString());
        conferenceData.setCreateRequest(conferenceRequest);
        event.setConferenceData(conferenceData);

        Event createdEvent = service.events().insert("primary", event)
                .setConferenceDataVersion(1)
                .execute();

        return createdEvent.getHangoutLink(); // Google Meet link
    }

    public static DateTime convertToGoogleDateTime(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(input, formatter);

        // Chuyển sang múi giờ Việt Nam (hoặc chỉnh theo nhu cầu)
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        Date date = Date.from(localDateTime.atZone(zone).toInstant());

        return new DateTime(date);
    }




}
