package com.calendargenerator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomeService {

    @Autowired
    public HomeService() {
    }

    public ResponseEntity<Map<String, String>> wakeUp() {
        return ResponseEntity.ok()
                .headers(generateHeaders())
                .body(new HashMap<String, String>() {{
                    put("Status", "OK");
                }});
    }

    private HttpHeaders generateHeaders() {
        return new HttpHeaders() {{
            add(HttpHeaders.ACCEPT_ENCODING, "gzip");
            add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            add(HttpHeaders.EXPIRES, "0");
        }};
    }
}