package com.calendargenerator.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(name = "Home",
        description = "Home controller",
        stage = ApiStage.ALPHA)
public class HomeController {

    @ApiMethod(description = "Redirect to main page")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String greet() {
        return "Add to URL above /calendar/{groupId} without curly brackets. For documentation navigate to {site-url}/jsondoc-ui.html and type http://{site-url}/jsondoc";
    }
}

