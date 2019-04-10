package com.calendargenerator.init;

import com.calendargenerator.dao.ComplexScheduleDAO;
import com.calendargenerator.model.ComplexSchedule;
import com.calendargenerator.model.ShortenedLecture;
import com.calendargenerator.model.SimpleSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private ComplexScheduleDAO complexScheduleDAO;

    @Autowired
    public DatabaseSeeder(ComplexScheduleDAO complexScheduleDAO) {
        this.complexScheduleDAO = complexScheduleDAO;
    }

    @Override
    public void run(String... args) throws Exception {
        ShortenedLecture shortenedLecture = new ShortenedLecture("initialTestData-Modelowanie danych", 2, true);
        ShortenedLecture shortenedLecture2 = new ShortenedLecture("initialTestData-Angielski", 1, true);
        SimpleSchedule simpleSchedule = new SimpleSchedule("2222", Arrays.asList(shortenedLecture, shortenedLecture2));
        SimpleSchedule simpleSchedule2 = new SimpleSchedule("1111", Arrays.asList(shortenedLecture, shortenedLecture));
        ComplexSchedule complexSchedule = new ComplexSchedule(Arrays.asList(simpleSchedule, simpleSchedule2));
        complexScheduleDAO.save(complexSchedule);
    }
}
