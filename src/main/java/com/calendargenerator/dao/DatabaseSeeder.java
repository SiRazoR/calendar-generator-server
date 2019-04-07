package com.calendargenerator.dao;

import com.calendargenerator.model.ShortenedLecture;
import com.calendargenerator.model.UekGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private GroupsDAO groupsDAO;

    @Autowired
    public DatabaseSeeder(GroupsDAO groupsDAO) {
        this.groupsDAO = groupsDAO;
    }

    @Override
    public void run(String... args) throws Exception {
        UekGroup group = new UekGroup("111111", Arrays.asList(new ShortenedLecture("initialTestData", 1, "true")));
        groupsDAO.save(group);
    }
}
