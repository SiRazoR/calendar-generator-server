package com.calendargenerator.service;

import com.calendargenerator.dao.CombinedGroupsDAO;
import com.calendargenerator.dao.GroupsDAO;
import com.calendargenerator.model.CombinedUekGroups;
import com.calendargenerator.model.UekGroup;
import com.calendargenerator.service.process.IcsCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CalendarService {

    private GroupsDAO groupsDAO;
    private CombinedGroupsDAO combinedGroupsDAO;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CalendarService(GroupsDAO groupsDAO, CombinedGroupsDAO combinedGroupsDAO) {
        this.groupsDAO = groupsDAO;
        this.combinedGroupsDAO = combinedGroupsDAO;
    }

    public ResponseEntity<String> getSchedule(String groupId) {
        IcsCalendar calendar = new IcsCalendar(groupId);
        return ResponseEntity.ok()
                .headers(generateHeaders())
                .body(calendar.toString());
    }

    public ResponseEntity<String> getModifiedSchedule(String calendarGeneratedId) {
        UekGroup uekGroup = groupsDAO.findById(UUID.fromString(calendarGeneratedId)).orElse(new UekGroup());
        IcsCalendar calendar = new IcsCalendar(uekGroup.getGroupId());
        uekGroup.getLecture().stream()
                .filter(element -> !element.isMandatory())
                .forEach(lecture -> calendar.removeLectures(lecture.getName(), lecture.getDayOfTheWeek()));

        return ResponseEntity.ok()
                .headers(generateHeaders())
                .body(calendar.toString());

    }
    public ResponseEntity<List> getDistinctLectures(String groupId) {
        IcsCalendar calendar = new IcsCalendar(groupId);
        return ResponseEntity.ok()
                .body(calendar.getDistinctLectures());
    }

    public ResponseEntity<Map> generateUniqueLink(UekGroup uekGroup) {
        groupsDAO.save(uekGroup);
        Map<String, String> response = new HashMap<String, String>() {
            {
                put("id", uekGroup.getId().toString());
            }
        };
        return ResponseEntity.ok()
                .body(response);
    }

    public ResponseEntity<List<UekGroup>> getAllGroupsFromDatabase() {
        return ResponseEntity.ok()
                .body(groupsDAO.findAll());
    }

    public ResponseEntity<List<CombinedUekGroups>> getAllCombinedGroupsFromDatabase() {
        return ResponseEntity.ok()
                .body(combinedGroupsDAO.findAll());
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
