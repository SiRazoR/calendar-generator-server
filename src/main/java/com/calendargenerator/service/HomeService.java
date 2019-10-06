package com.calendargenerator.service;

import org.springframework.beans.factory.annotation.Autowired;
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
                .body(new HashMap<String, String>() {{
                    put("Status", "OK");
                }});
    }
}