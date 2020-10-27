package gov.kui.jmssender.controller;

import gov.kui.jmssender.model.DocumentDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JmsController {

    private List<DocumentDto> documentDtoList = new ArrayList<>();

    @GetMapping({"/","/sender"})
    public String startPage(Model model){
        model.addAttribute("newDocument", new DocumentDto());
        model.addAttribute("documentDtoList", documentDtoList);
        return "sender";
    }

    @PostMapping("/sender")
    public void submitDocument(DocumentDto documentDto, Model model){
        System.out.println("documentDto: "+ documentDto);
        documentDtoList.add(documentDto);
        startPage(model);
    }
}
