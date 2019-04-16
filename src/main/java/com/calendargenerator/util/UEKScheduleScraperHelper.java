package com.calendargenerator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UEKScheduleScraperHelper {
    private static Logger log = LoggerFactory.getLogger(UEKScheduleScraperHelper.class);

    private UEKScheduleScraperHelper() {
        throw new UnsupportedOperationException();
    }

    //TODO
    //App will contain more scrapers than just LectureScraper
    //It will be necessary to scrap every course of my university with every group that is created
    //There will be many places with similar code
    //Util class will help to prevent code repetition
}
