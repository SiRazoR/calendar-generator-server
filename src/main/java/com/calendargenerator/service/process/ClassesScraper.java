package com.calendargenerator.service.process;

import com.calendargenerator.exception.DataNotFoundException;
import com.calendargenerator.exception.GenericException;
import com.calendargenerator.model.Classes;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class ClassesScraper {
    private Logger log;
    private SimpleDateFormat dataFormatter;

    ClassesScraper() {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.dataFormatter = new SimpleDateFormat("yyyy-MM-ddHH:mm");
    }

    List<Classes> getLectureList(String groupId) {
        int periodType = 4;
        return getScrapedClassesList(groupId, periodType);
    }

    private List<Classes> getScrapedClassesList(String groupId, int period) {
        List<Classes> list = new ArrayList<>();
        try {
            if (period == 0)
                throw new DataNotFoundException("Group ID may be invalid, found 0 classes on provided data: " + groupId);

            String url = "http://planzajec.uek.krakow.pl/index.php?typ=G&id=" + groupId + "&okres=" + period;
            log.info("Trying to scrap schedule for period: " + period + " with URL: " + url);

            final Document document = Jsoup.connect(url).get();
            for (Element row : document.select("tr")) {
                if (row.select(".termin").text().isEmpty())
                    continue;
                String classesDate = row.select(".termin").text();
                String classesDuration = row.select(".dzien").text();
                String classesStartHour = classesDuration.substring(3, 8);
                String classesEndHour = classesDuration.substring(11, 16);
                String classesType = parseClassesType(row.select("td:eq(3)").text());
                Calendar classesStartCallendar = toCalendar(dataFormatter.parse(classesDate + classesStartHour));
                Calendar classesEndCallendar = toCalendar(dataFormatter.parse(classesDate + classesEndHour));
                String classesName = classesType + row.select("td:eq(2)").text();
                String instructor = row.select("td:eq(4)").text();
                String location = row.select("td:eq(5)").text();

                list.add(new Classes(classesStartCallendar, classesEndCallendar, classesName, instructor, location));
            }
            if (list.isEmpty()) {
                return getScrapedClassesList(groupId, --period);
            }
        } catch (IOException | ParseException e) {
            throw new GenericException(e.getMessage());
        }

        log.info("Found " + list.size() + " classes");
        return list;
    }

    private Calendar toCalendar(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
        return calendar;
    }

    private String parseClassesType(String text) {
        if (text.toLowerCase().contains("egzamin"))
            return "EGZAMIN";
        else
            return "[" + Character.toUpperCase(text.charAt(0)) + "]";
    }
}
