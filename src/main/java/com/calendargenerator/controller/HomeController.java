package com.calendargenerator.controller;

import com.calendargenerator.service.HomeService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@RestController
@Api(name = "Home",
        description = "Home controller",
        stage = ApiStage.ALPHA)
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @ApiMethod(description = "Redirect to documentation page")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showDocumentation() {
        return new ModelAndView("redirect:" + "https://uek-calendar-generator.herokuapp.com/jsondoc-ui.html");
    }

    @ApiMethod(description = "To wake up API that is deployed on Heroku")
    @RequestMapping(value = "/wake", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> wake() {
        return homeService.wakeUp();
    }
}

