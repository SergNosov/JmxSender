package gov.kui.jmssender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class JmsController {

    @GetMapping("/sender")
    public String startPage(Model theModel){
        return "sender";
    }
}
