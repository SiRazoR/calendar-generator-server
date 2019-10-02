package com.calendargenerator.service;

import com.calendargenerator.dao.ComplexScheduleDAO;
import com.calendargenerator.exception.DataNotFoundException;
import com.calendargenerator.model.ComplexSchedule;
import com.calendargenerator.model.Schedule;
import com.calendargenerator.model.SimpleSchedule;
import com.calendargenerator.service.process.GroupScraper;
import com.calendargenerator.service.process.IcsCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalendarService {

    private ComplexScheduleDAO complexScheduleDAO;

    @Autowired
    public CalendarService(ComplexScheduleDAO complexScheduleDAO) {
        this.complexScheduleDAO = complexScheduleDAO;
    }

    public ResponseEntity<String> getSchedule(String groupId) {
        IcsCalendar calendar = new IcsCalendar(groupId);
        return ResponseEntity.ok()
                .headers(generateHeaders())
                .body(calendar.getStringRepresentation());
    }

    public ResponseEntity<String> getModifiedSchedule(String uuid) {
        ComplexSchedule complexSchedule = complexScheduleDAO.findById(UUID.fromString(uuid)).orElse(null);
        if (complexSchedule == null)
            throw new DataNotFoundException("Provided UUID: {" + uuid + "} is invalid");

        IcsCalendar calendar = new IcsCalendar();
        complexSchedule.getGroups().stream()
                .map(this::handleSimpleSchedule)
                .forEach(calendar::concatenateLectureList);

        return ResponseEntity.ok()
                .headers(generateHeaders())
                .body(calendar.getStringRepresentation());
    }

    private IcsCalendar handleSimpleSchedule(SimpleSchedule simpleSchedule) {
        IcsCalendar calendar = new IcsCalendar(simpleSchedule.getGroupId());
        simpleSchedule.getLecture().stream()
                .filter(element -> !element.isMandatory())
                .forEach(lecture -> calendar.removeLectures(lecture.getName(), lecture.getDayOfTheWeek()));
        return calendar;
    }

    public ResponseEntity<Map<String, String>> getGroupsWithId() {
        return ResponseEntity.ok()
                .body(new GroupScraper().getGroups());
    }

    public ResponseEntity<SimpleSchedule> getDistinctLectures(String groupId) {
        IcsCalendar calendar = new IcsCalendar(groupId);
        return ResponseEntity.ok()
                .body(new SimpleSchedule(groupId, calendar.getDistinctLectures()));
    }

    public ResponseEntity<Map> generateUniqueLink(Schedule schedule) {
        String scheduleId;
        if (schedule instanceof SimpleSchedule) {
            ComplexSchedule complexSchedule = new ComplexSchedule(Collections.singletonList((SimpleSchedule) schedule));
            scheduleId = complexSchedule.getId().toString();
            complexScheduleDAO.save(complexSchedule);
        } else {
            scheduleId = schedule.getId().toString();
            complexScheduleDAO.save((ComplexSchedule) schedule);
        }
        Map<String, String> response = new HashMap<String, String>() {
            {
                put("id", scheduleId);
            }
        };
        return ResponseEntity.ok()
                .body(response);
    }

    public ResponseEntity<List<ComplexSchedule>> getAllComplexSchedulesFromDatabase() {
        return ResponseEntity.ok()
                .body(complexScheduleDAO.findAll());
    }

    public ResponseEntity<Optional<ComplexSchedule>> getComplexScheduleFromDatabase(String uuid) {
        Optional<ComplexSchedule> response = complexScheduleDAO.findById(UUID.fromString(uuid));
        if (!response.isPresent())
            throw new DataNotFoundException("Provided UUID: {" + uuid + "} is invalid");

        return ResponseEntity.ok()
                .body(response);
    }

    private HttpHeaders generateHeaders() {
        return new HttpHeaders() {{
            add(HttpHeaders.ACCEPT_ENCODING, "gzip");
            add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
            add(HttpHeaders.EXPIRES, "0");
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=classes.ics");
        }};
    }
}
