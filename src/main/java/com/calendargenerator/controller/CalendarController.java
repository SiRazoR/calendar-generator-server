package com.calendargenerator.controller;

import com.calendargenerator.model.UekGroup;
import com.calendargenerator.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CalendarController {

    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String greet() {
        return "Add group id to URL above after /";
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> schedule(@PathVariable String groupId) {
        return calendarService.getSchedule(groupId);
    }

    @RequestMapping(value = "/distinct/{groupId}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<List> distinctSchedule(@PathVariable String groupId) {
        return calendarService.getDistinctLectures(groupId);
    }

    @RequestMapping(value = "/modified/{calendarGeneratedId}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modifiedSchedule(@PathVariable String calendarGeneratedId) {
        return calendarService.getModifiedSchedule(calendarGeneratedId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> schedule(@RequestBody List<UekGroup> uekGroupList) {
        return calendarService.generateUniqueLink(uekGroupList);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<UekGroup>> getAll() {
        return calendarService.getDatabase();
    }
}

