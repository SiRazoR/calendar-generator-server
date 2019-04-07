package com.calendargenerator.init;

import com.calendargenerator.dao.CombinedGroupsDAO;
import com.calendargenerator.dao.GroupsDAO;
import com.calendargenerator.model.CombinedUekGroups;
import com.calendargenerator.model.ShortenedLecture;
import com.calendargenerator.model.UekGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private GroupsDAO groupsDAO;
    private CombinedGroupsDAO combinedGroupsDAO;

    @Autowired
    public DatabaseSeeder(GroupsDAO groupsDAO, CombinedGroupsDAO combinedGroupsDAO) {
        this.groupsDAO = groupsDAO;
        this.combinedGroupsDAO = combinedGroupsDAO;
    }

    @Override
    public void run(String... args) throws Exception {
        ShortenedLecture shortenedLecture = new ShortenedLecture("initialTestData-Modelowanie danych", 2, true);
        ShortenedLecture shortenedLecture2 = new ShortenedLecture("initialTestData-Angielski", 1, true);
        UekGroup uekGroup = new UekGroup("2222", Arrays.asList(shortenedLecture, shortenedLecture2));
        UekGroup uekGroup2 = new UekGroup("1111", Arrays.asList(shortenedLecture, shortenedLecture));
        CombinedUekGroups combinedUekGroups = new CombinedUekGroups(Arrays.asList(uekGroup, uekGroup2));
        groupsDAO.save(uekGroup);
        combinedGroupsDAO.save(combinedUekGroups);
    }
}
