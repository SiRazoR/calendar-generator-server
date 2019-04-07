package com.calendargenerator.service.process;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.component.VTimezone;
import biweekly.io.TimezoneAssignment;
import biweekly.io.TimezoneInfo;
import biweekly.property.Method;
import com.calendargenerator.model.Lecture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

public class IcsCalendar {
    private Logger log;
    private ICalendar ical;
    private LecturesScraper scraper;
    private List<Lecture> lectures;

    private IcsCalendar() {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.ical = new ICalendar();
        this.ical.setMethod(Method.REQUEST);
        this.ical.setTimezoneInfo(getWarsawTimeZoneInfo());
        this.scraper = new LecturesScraper();
    }

    public IcsCalendar(String groupId) {
        this();
        this.lectures = scraper.getLectureList(groupId);
    }

    private TimezoneInfo getWarsawTimeZoneInfo() {
        TimeZone timezone = TimeZone.getTimeZone("Europe/Warsaw");
        VTimezone component = new VTimezone("Europe/Warsaw");
        TimezoneInfo timezoneInfo = new TimezoneInfo();
        timezoneInfo.setDefaultTimezone(new TimezoneAssignment(timezone, component));
        return timezoneInfo;
    }

    private void generateIcsCalendar() {
        this.lectures.forEach(element -> ical.addEvent(lecturesToCalendarEvent(element)));
        log.info("iCalendar generated with " + lectures.size() + " classes");
    }

    private VEvent lecturesToCalendarEvent(Lecture lecture) {
        biweekly.component.VEvent event = new biweekly.component.VEvent();
        event.setSummary(lecture.getName());
        event.setLocation(lecture.getLocation());
        event.setDateStart(lecture.getStartDateCalendar().getTime());
        event.setDateEnd(lecture.getEndDateCalendar().getTime());
        event.setDescription(lecture.getInstructor());
        return event;
    }

    public void removeLectures(String lectureName) {
        this.lectures.removeIf(element -> element.getName().contains(lectureName));
        log.info(lectureName + " was succesfully deleted");
    }

    public void removeLectures(String lectureName, Integer lectureDayOfWeek) {
        this.lectures.removeIf(element -> element.getName().contains(lectureName) &&
                element.getStartDateCalendar().get(Calendar.DAY_OF_WEEK) == lectureDayOfWeek);
        log.info(lectureName + " on " + new DateFormatSymbols().getWeekdays()[lectureDayOfWeek] + " was succesfully deleted");
    }

    public List getDistinctLectures() {
        return lectures.stream()
                .map(lecture -> Map.of(lecture.getName(),lecture.getStartDateCalendar().get(Calendar.DAY_OF_WEEK)))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        generateIcsCalendar();
        log.info("Proceed to generate output string");
        return Biweekly.write(ical).go();
    }
}

