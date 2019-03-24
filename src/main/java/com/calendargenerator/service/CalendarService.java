package com.calendargenerator.service;

import com.calendargenerator.service.process.IcsCalendar;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {

    public ResponseEntity<String> getSchedule(String groupId) throws Exception {
        IcsCalendar calendar = new IcsCalendar(groupId);

        HttpHeaders headers = new HttpHeaders() {{
            add(HttpHeaders.ACCEPT_ENCODING, "gzip");
            add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
            add(HttpHeaders.EXPIRES, "0");
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=classes.ics");
        }};

        return ResponseEntity.ok()
                .headers(headers)
                .body(calendar.toString());
    }
}
