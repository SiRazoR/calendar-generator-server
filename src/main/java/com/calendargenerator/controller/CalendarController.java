package com.calendargenerator.controller;

import com.calendargenerator.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @RequestMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String greet() {
        return "Add group id to URL above after /";
    }

    @RequestMapping(value = "/{groupId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> schedule(@PathVariable String groupId) {
        return calendarService.getSchedule(groupId);
    }
}

