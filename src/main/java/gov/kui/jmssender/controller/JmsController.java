package gov.kui.jmssender.controller;

import gov.kui.jmssender.model.DocumentPrototype;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JmsController {

    @GetMapping({"/","/sender"})
    public String startPage(Model model){
        model.addAttribute("newDocument", new DocumentPrototype());
        return "sender";
    }

    @PostMapping("/sender")
    public void submitDocument(DocumentPrototype documentPrototype, Model model){
        System.out.println("documentPrototype: "+ documentPrototype);
        startPage(model);
    }
}
