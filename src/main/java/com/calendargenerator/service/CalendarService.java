package com.calendargenerator.service;

import com.calendargenerator.dao.GroupsDAO;
import com.calendargenerator.model.UekGroup;
import com.calendargenerator.service.process.IcsCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {

    private GroupsDAO groupsDAO;
    private HttpHeaders headers;

    @Autowired
    public CalendarService(GroupsDAO groupsDAO) {
        this.groupsDAO = groupsDAO;
        this.headers = generateHeaders();
    }

    public ResponseEntity<String> getSchedule(String groupId) {
        IcsCalendar calendar = new IcsCalendar(groupId);

        return ResponseEntity.ok()
                .headers(headers)
                .body(calendar.toString());
    }

    public ResponseEntity<List> getDistinctLectures(String groupId) {
        IcsCalendar calendar = new IcsCalendar(groupId);

        return ResponseEntity.ok()
                .headers(headers)
                .body(calendar.getDistinctLectures());
    }

    public ResponseEntity<String> generateUniqueLink(List<UekGroup> uekGroupList) {
        //add to database groupList, return 1251726587961 ID

        return ResponseEntity.ok()
                .headers(headers)
                .body(null);
    }

    public ResponseEntity<List<UekGroup>> getDatabase() {
        //add to database groupList, return 1251726587961 ID

        return ResponseEntity.ok()
                .body(groupsDAO.findAll());
    }

    public ResponseEntity<String> getModifiedSchedule(String calendarGeneratedId) {
        //get from database groupList
        //scrap calendar
        //for( non mandatory ) removeLecture
        //return calendar
        return ResponseEntity.ok()
                .headers(headers)
                .body(null);
    }

    private HttpHeaders generateHeaders() {
        return  new HttpHeaders() {{
            add(HttpHeaders.ACCEPT_ENCODING, "gzip");
            add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
            add(HttpHeaders.EXPIRES, "0");
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=classes.ics");
        }};
    }
}
