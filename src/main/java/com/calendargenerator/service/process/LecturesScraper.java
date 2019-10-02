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
    private static final int START_PERIOD = 4;
    private static final String URL = "http://planzajec.uek.krakow.pl/index.php?typ=G&id=%s&okres=%d";
    private static final String TABLE_ROW = "tr";
    private static final String TABLE_DATA_NUMBER = "td:eq(%d)";
    private static final String TERM = ".termin";
    private static final String DAY = ".dzien";
    private static final String GROUP = ".grupa";
    private static final String DATE_PATTERN = "yyyy-MM-ddHH:mm";
    private static final String TIMEZONE = "Europe/Warsaw";

    LecturesScraper() {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.dataFormatter = new SimpleDateFormat(DATE_PATTERN);
    }

    List<Lecture> getLectureList(String groupId) {
        return getLectureList(groupId, START_PERIOD);
    }

    private List<Lecture> getLectureList(String groupId, int period) {
        List<Lecture> lectures = new ArrayList<>();
        try {
            if (period == 0)
                throw new DataNotFoundException("Group ID may be invalid, found 0 classes on provided data: {" + groupId + "}");

            final Document document = Jsoup.connect(String.format(URL, groupId, period)).get();
            validateGroupId(document, groupId);

            log.info("Trying to scrap schedule for period: " + period);
            for (Element row : document.select(TABLE_ROW)) {
                if (row.select(TERM).text().isEmpty()) {
                    continue;
                }

                String name = String.format("%s%s",
                        parseLectureType(row.select(String.format(TABLE_DATA_NUMBER, 3)).text()),
                        row.select(String.format(TABLE_DATA_NUMBER, 2)).text());
                String instructor = String.format("%s",
                        row.select(String.format(TABLE_DATA_NUMBER, 4)).text());
                String location = String.format("%s",
                        row.select(String.format(TABLE_DATA_NUMBER, 5)).text());
                Calendar startDateCalendar = toCalendar(dataFormatter.parse(row.select(TERM).text() + row.select(DAY).text().substring(3, 8)));
                Calendar endDateCalendar = toCalendar(dataFormatter.parse(row.select(TERM).text() + row.select(DAY).text().substring(11, 16)));

                lectures.add(
                        Lecture.builder()
                                .name(name)
                                .instructor(instructor)
                                .location(location)
                                .startDateCalendar(startDateCalendar)
                                .endDateCalendar(endDateCalendar)
                                .build()
                );
            }

            if (lectures.isEmpty()) {
                log.info("Found empty page, decreasing period number");
                return getLectureList(groupId, --period);
            }
        } catch (IOException | ParseException e) {
            throw new GenericException(e.getMessage());
        }
        log.info("Found " + lectures.size() + " classes");
        return lectures;
    }

    private void validateGroupId(Document document, String groupId) {
        if (document.select(GROUP).first().text().isEmpty())
            throw new DataNotFoundException("Group ID {" + groupId + "} is invalid");
    }

    private String parseLectureType(String text) {
        if (text.toLowerCase().contains("egzamin"))
            return "EGZAMIN";
        else
            return "[" + Character.toUpperCase(text.charAt(0)) + "]";
    }

    private Calendar toCalendar(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        return calendar;
    }
}
