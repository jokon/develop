package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.Calculation;


/**
 * Created by jaok on 2016-11-03.
 */

@RestController
public class ApiController {

    @RequestMapping("/api")
    public String help() {
        return "First attempt!";
    }
}
