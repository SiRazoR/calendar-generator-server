package com.calendargenerator.service.process;

import com.calendargenerator.exception.DataNotFoundException;
import com.calendargenerator.exception.GenericException;
import com.calendargenerator.model.Lecture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class LecturesScraper {
    private Logger log;
    private SimpleDateFormat dataFormatter;

    LecturesScraper() {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.dataFormatter = new SimpleDateFormat("yyyy-MM-ddHH:mm");
    }

    List<Lecture> getLectureList(String groupId) {
        int periodType = 4;
        return getScrapedLecturesList(groupId, periodType);
    }

    private List<Lecture> getScrapedLecturesList(String groupId, int period) {
        List<Lecture> list = new ArrayList<>();
        String url = "http://planzajec.uek.krakow.pl/index.php?typ=G&id=" + groupId + "&okres=" + period;
        log.info("Trying to scrap schedule for period: " + period + " with URL: " + url);

        try {
            if (period == 0)
                throw new DataNotFoundException("Group ID may be invalid, found 0 classes on provided data: " + groupId);

            final Document document = Jsoup.connect(url).get();
            validateGroupId(document);

            for (Element row : document.select("tr")) {
                if (row.select(".termin").text().isEmpty()) {
                    continue;
                }
                String lectureDate = row.select(".termin").text();
                String lectureDuration = row.select(".dzien").text();
                String lectureStartHour = lectureDuration.substring(3, 8);
                String lectureEndHour = lectureDuration.substring(11, 16);
                String lectureType = parseLectureType(row.select("td:eq(3)").text());
                Calendar lectureStartCalendar = toCalendar(dataFormatter.parse(lectureDate + lectureStartHour));
                Calendar lectureEndCalendar = toCalendar(dataFormatter.parse(lectureDate + lectureEndHour));
                String lectureName = lectureType + row.select("td:eq(2)").text();
                String instructor = row.select("td:eq(4)").text();
                String location = row.select("td:eq(5)").text();

                list.add(new Lecture(lectureStartCalendar, lectureEndCalendar, lectureName, instructor, location));
            }
            if (list.isEmpty()) {
                log.info("Found empty page, decreasing period number");
                return getScrapedLecturesList(groupId, --period);
            }
        } catch (IOException | ParseException e) {
            throw new GenericException(e.getMessage());
        }

        log.info("Found " + list.size() + " classes");
        return list;
    }

    private void validateGroupId(Document document) {
        for (Element row : document.select(".grupa")) {
            log.info("Checking if group id is valid");
            if (row.text().isEmpty())
                throw new DataNotFoundException("Group ID is invalid");
            log.info("Found group: " + row.text());
        }
    }

    private Calendar toCalendar(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        return calendar;
    }

    private String parseLectureType(String text) {
        if (text.toLowerCase().contains("egzamin"))
            return "EGZAMIN";
        else
            return "[" + Character.toUpperCase(text.charAt(0)) + "]";
    }
}
