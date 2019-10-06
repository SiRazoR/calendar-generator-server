package com.calendargenerator.service.process;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.component.VTimezone;
import biweekly.io.TimezoneAssignment;
import biweekly.io.TimezoneInfo;
import biweekly.property.Method;
import com.calendargenerator.model.Lecture;
import com.calendargenerator.model.ShortenedLecture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class IcsCalendar {
    private Logger log;
    private ICalendar ical;
    private List<Lecture> lectures;
    private final boolean MANDATORY = true;
    private static final String TIMEZONE = "Europe/Warsaw";

    public IcsCalendar() {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.ical = new ICalendar();
        this.ical.setMethod(Method.REQUEST);
        this.ical.setTimezoneInfo(getWarsawTimeZoneInfo());
    }

    public IcsCalendar(String groupId) {
        this();
        this.lectures = new LecturesScraper().getLectureList(groupId);
    }

    private TimezoneInfo getWarsawTimeZoneInfo() {
        TimeZone timezone = TimeZone.getTimeZone(TIMEZONE);
        VTimezone component = new VTimezone(TIMEZONE);
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

    public void removeLectures(String lectureName, Integer lectureDayOfWeek) {
        this.lectures.removeIf(element -> element.getName().contains(lectureName) &&
                element.getStartDateCalendar().get(Calendar.DAY_OF_WEEK) == lectureDayOfWeek);
        log.info(lectureName + " on " + new DateFormatSymbols().getWeekdays()[lectureDayOfWeek] + " was succesfully deleted");
    }

    public List<ShortenedLecture> getDistinctLectures() {
        return lectures.stream()
                .map(lecture -> new ShortenedLecture(lecture.getName(), lecture.getStartDateCalendar().get(Calendar.DAY_OF_WEEK), MANDATORY))
                .distinct()
                .collect(Collectors.toList());
    }

    public void concatenateLectureList(IcsCalendar calendar) {
        log.info("Adding " + calendar.lectures.size() + " lectures to the list");
        if (this.lectures == null)
            this.lectures = calendar.lectures;
        else
            this.lectures.addAll(calendar.lectures);
    }

    public String getStringRepresentation() {
        generateIcsCalendar();
        log.info("Proceed to generate output string");
        return Biweekly.write(ical).go();
    }
}

