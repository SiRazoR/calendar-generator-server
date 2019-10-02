package com.calendargenerator.service.process;

import com.calendargenerator.exception.GenericException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupScraper {
    private static final String HREF_ATTRIBUTE = "href";
    private static final String GROUP_QUERRY = ".kolumny a";
    private static final String ID_REGEX = "^.*id=";
    private static final String PERIOD_REGEX = "&okres.*";
    private static final String EMPTY = "";
    private static final String GROUP_LIST_URL = "http://planzajec.uek.krakow.pl/index.php?typ=G&;grupa=%";

    public Map<String, String> getGroups() {
        try {
            return Jsoup.connect(GROUP_LIST_URL).get().select(GROUP_QUERRY).stream().collect(Collectors.toMap(
                    (Element group) -> group.attr(HREF_ATTRIBUTE).replaceAll(ID_REGEX, EMPTY).replaceAll(PERIOD_REGEX, EMPTY),
                    Element::text
            ));
        } catch (IOException e) {
            throw new GenericException(e.getMessage());
        }
    }
}
